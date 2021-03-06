from sklearn.tree import DecisionTreeClassifier, export_graphviz
from sklearn.tree import DecisionTreeRegressor
from sklearn.linear_model import LinearRegression
from sklearn import tree
import subprocess
import pandas as pd
import numpy as np
import math
import scipy.stats as st
import matplotlib.pyplot as plt
import graphviz
from sklearn import svm
from sklearn import metrics
import time
from datetime import datetime, timedelta
from sklearn.naive_bayes import GaussianNB
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import BaggingClassifier
from sklearn.neighbors import KNeighborsClassifier
from random import random
from random import seed
import seaborn as sns
import threading
import concurrent.futures

# X = source a list of feature vectors that contain the values of Moving Averages (200, 50), RSI, On-Balance Volume, MACD
# Y = source a list of price movements in percentage for n (start with 5) day periods
# fit and see what happens


def load_technical_data(ticker, function, extended_function_name=""):
    path = "./Stock_Data/" + ticker + "_" + \
        function + extended_function_name + "Data.csv"
    data = pd.read_csv(path)
    time_period_data = []

    # row[0] is the date of record. row[1] is the value for the technical indicator at the date of record.
    for row in data[::-1].iterrows():
        # print(row)
        # print(data.loc[i])
        if function == "STOCH":
            # , row["SlowK"] TODO: work this so it gives buy and sell signals based on the %d crossing the %k
            time_period_data.append((row[0], row["SlowD"]))
        else:
            # print((row[-1]['time']))
            time_period_data.append((row[-1]['time'], row[-1][function]))

    return time_period_data


def get_earliest_date(ticker, technicals, extended_function_name=""):
    earliest_date = None
    time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                              ticker + "_TIME_SERIES_DAILYData.csv", index_col=0)

    for d, _ in time_series[::-1].iterrows():

        earliest_date = d

        # only need the first value. [::-1] makes it start from the earliest date (the last one in the file) so this is the only date we need to compare
        break

    for tech in technicals:
        data = pd.read_csv("./Stock_Data/" + ticker + "_" +
                           tech + extended_function_name + "Data.csv", index_col=0)
        for d, _ in data[::-1].iterrows():

            if earliest_date == None:
                earliest_date = d
            elif d > earliest_date:  # we actually want the lastest earliest date in each file. AKA We need the earliest date the is present in ALL files
                earliest_date = d

            # only need the first value. [::-1] makes it start from the earliest date (the last one in the file) so this is the only date we need to compare
            break

    return earliest_date


def get_latest_date(ticker, technicals, extended_function_name=""):
    latest_date = None
    time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                              ticker + "_TIME_SERIES_DAILYData.csv", index_col=0)

    for d, _ in time_series.iterrows():

        latest_date = d

        # only need the first value. [::-1] makes it start from the earliest date (the last one in the file) so this is the only date we need to compare
        break

    for tech in technicals:
        data = pd.read_csv("./Stock_Data/" + ticker + "_" +
                           tech + extended_function_name + "Data.csv", index_col=0)
        for d, _ in data.iterrows():

            if latest_date == None:
                latest_date = d
            elif d < latest_date:  # we actually want the lastest earliest date in each file. AKA We need the earliest date the is present in ALL files
                latest_date = d

            # only need the first value. [::-1] makes it start from the earliest date (the last one in the file) so this is the only date we need to compare
            break

    return latest_date


def get_1std_dev(ticker, year, time_period=5):
    time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                              ticker + "_TIME_SERIES_DAILYData.csv", index_col=0)
    price_deltas = []  # clear the price deltas
    i = 0  # clear the counter
    first_price = 0
    second_price = 0

    # reformat the year string to make sure

    last_day_of_year = year.split("-")
    last_day_of_year[1] = "12"  # change the month to december
    # change the day to 31. The last day in the year, so that we caputre the full year of data
    last_day_of_year[2] = "31"

    last_day_of_year = "-".join(last_day_of_year)

    for date, row in time_series[::-1].iterrows():
        if date < year:
            continue
        elif date > last_day_of_year:
            break
        if i == 0:
            first_price = float(row["close"])
        elif i % time_period == 0:

            second_price = float(row["close"])
            # how many percents has the ticker moved since the last time period (that wasn't 0)
            percent_change = (second_price - first_price) / first_price
            price_deltas.append(percent_change)
            first_price = float(row["close"])
        i += 1

    price_deltas = np.array(price_deltas)
    std_dev = price_deltas.std()
    mean = price_deltas.mean()
    return std_dev, mean


def get_prob_of_move(move, mean, std_dev):
    z_score = (move - mean) / std_dev
    p_values = st.norm.sf(abs(z_score))
    return z_score, p_values


def get_data(tickers, technicals, time_period=5, percent_movement_threshold=None, std_dev_threshold=None, noise_ratio=2):
    if percent_movement_threshold == None and std_dev_threshold == None:
        print("you must specify a percent_movement_threshold or a std_dev_threshold, suggested values are -0.05% and 1 standard deviation")
        return
    data = []
    total_real_drops = 0
    price_deltas = []
    labels = []
    volumes = []
    dates = []
    ticker_data = []
    i = 0

    for ticker in tickers:
        time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                                  ticker + "_TIME_SERIES_DAILYData.csv", index_col=0)

        first_price = None
        first_date = ""
        technical_data_maps = []

        for technical in technicals:
            technical_data_maps.append(load_technical_data(ticker, technical))

        earliest_date = get_earliest_date(ticker, technicals)
        latest_date = get_latest_date(ticker, technicals)

        if std_dev_threshold != None:
            # if someone inputs 1 then the threshold is 1 std dev, if they input 0.8 its 80% of one std dev, if they input 2 its 2 whole std devs
            std_dev, mean = get_1std_dev(ticker, earliest_date, 1)
            new_std_dev_threshold = std_dev * float(std_dev_threshold)

        for date, row in time_series[::-1].iterrows():
            # print("DATE: ", row[0])
            # print(row)

            if date < earliest_date:
                continue  # keep skipping iterations of this  loop until we get to the first date present in all data files. This way we avoid key errors and matching data from different dates into a single feature vector
            if date > latest_date:
                continue  # after I stopped cleaning the data because of the above check it became apparant that some indicators had data up to a more recent date which produced the same key error

            if first_price == None:
                first_price = float(row["close"])

                first_date = date
                # print("firts_date:", first_date)

            if i % time_period == 0:
                curr_date = date
                # print("first/curr_date:", first_date, curr_date)
                curr_price = float(row["close"])
                percent_move = (curr_price - first_price) / first_price

                # refresh the std dev every year, maybe this helps
                if std_dev_threshold != None and curr_date.split("-")[0] > first_date.split("-")[0]:
                    # if someone inputs 1 then the threshold is 1 std dev, if they input 0.8 its 80% of one std dev, if they input 2 its 2 whole std devs
                    std_dev, mean = get_1std_dev(ticker, earliest_date, 1)
                    new_std_dev_threshold = std_dev * float(std_dev_threshold)

                if percent_movement_threshold != None:

                    if percent_move == 0:
                        percent_move += 0.00000001

                    if percent_movement_threshold > 0 and percent_move >= percent_movement_threshold:
                        total_real_drops += 1
                        label = 1

                    elif percent_movement_threshold < 0 and percent_move <= percent_movement_threshold:
                        # d1 = datetime.strptime(first_date, '%Y-%m-%d')
                        # d2 = datetime.strptime(curr_date, '%Y-%m-%d')
                        # print("percent move =", curr_price, "-", first_price, "/", first_price)
                        # print(ticker + "        ", percent_move, "% move between ", first_date,
                        #   " and ", curr_date, "(", (d2-d1), ")", first_price, curr_price, "rsi: ",rsi_data[first_date], "adx: ", adx_data[first_date], "sar: ", sar_data[first_date], "atr", atr_data[first_date])
                        total_real_drops += 1

                        label = 1
                    else:
                        label = -1
                elif std_dev_threshold != None:

                    if new_std_dev_threshold > 0 and percent_move > new_std_dev_threshold:
                        label = 1
                        total_real_drops += 1
                    elif new_std_dev_threshold < 0 and percent_move < new_std_dev_threshold:
                        # print("appending a move of ", percent_move, "for", ticker)
                        label = 1
                        total_real_drops += 1
                    else:
                        label = -1

                else:
                    label = -1

                # a high noise ratio results in fewer real_drops being mandated to be in the data. E.x. a noise_ratio of 2 means 50 % real drops, 50 % non-real drops (movements less that do not meet the threshold requirement)
                # only add data points if its a real drop data point or if theres less than 2 * the total number of total drops in data
                if len(data) < noise_ratio * total_real_drops or label == 1:

                    ticker_data.append(ticker)
                    labels.append(label)
                    volumes.append(data["volume"])
                    # print("appending", ticker, "first_date: ", first_date, " curr_date:", curr_date, [rsi_data[first_date], adx_data[first_date], sar_data[first_date], atr_data[first_date]])
                    dates.append((first_date, curr_date))

                    feature_vector = []
                    exception_occured = False

                    for technical_map in technical_data_maps:

                        index = binary_search(technical_map, 0, len(
                            technical_map), 0, first_date)

                        if index != -1:
                            feature_vector.append(technical_map[index])

                    data.append(feature_vector)

                first_price = curr_price
                first_date = curr_date

                # print(percent_move)
            i += 1

    print("total_real_drops (in all data): ", total_real_drops,
          "out of a total of", len(data), "data points")
    # print("len(ticker_data): ", len(ticker_data))
    # print("len = ", len(price_deltas))
    return data, labels, dates, ticker_data, volumes


def amalgamate_data(*datas):

    amalgamation = []
    # print("len(datas) = ", len(datas))
    for i in range(len(datas)):  # all datas should be the same length!
        amalgamated_row = []
        # print("data: ", i, datas[i])
        # print("len(data)", len(datas[i]))

        for j in range(len(datas[i])):
            # print("amalgamating j = ", j, 'data = ', datas[i][j])
            amalgamated_row.append(datas[i][j])
        # print(amalgamated_row)
        amalgamation.append(amalgamated_row)
    return amalgamation


def visualize_tree(tree, feature_names):
    """Create tree png using graphviz.

    Args
    ----
    tree -- scikit-learn DecsisionTree.
    feature_names -- list of feature names.
    """
    with open("dt.dot", 'w') as f:
        dot_data = export_graphviz(tree, out_file=f,
                                   feature_names=feature_names,
                                   class_names=["<10%", ">=10%"])

    command = ["dot", "-Tpng", "dt.dot", "-o", "dt.png"]
    try:
        subprocess.check_call(command)
    except:
        exit("Could not run dot, ie graphviz, to "
             "produce visualization")


def train_and_plot_svm(X, y):
    # now transform the old training data into just two dimensions since it seems that SVM can only plot two in two dimensions.
    # also 2 dimensions (adx, sar) are showing to be the most important with feature importances of 0.922 and 0.07 respectively

    for i in range(len(X)):
        X[i] = X[i][1:3]
        # print("new X[i].shape: ", len(X[i]))

    X = np.array(X)
    y = np.array(y)
    h = .02  # step size in the mesh

    # we create an instance of SVM and fit out data. We do not scale our
    # data since we want to plot the support vectors
    C = 1.0  # SVM regularization parameter
    start = time.time()
    svc = svm.SVC(kernel='linear', C=C, gamma="scale").fit(X, y)
    end = time.time()
    print("svm (linear kernel) train time: ", (end-start))
    start = time.time()
    rbf_svc = svm.SVC(kernel='rbf', C=C, gamma="scale").fit(X, y)
    end = time.time()
    print("svm (rbf kernel) train time: ", (end-start))
    start = time.time()
    poly_svc = svm.SVC(kernel='poly', degree=3, C=C, gamma="scale").fit(X, y)
    end = time.time()
    print("svm (poly kernel) train time: ", (end-start))
    start = time.time()
    lin_svc = svm.LinearSVC(C=C).fit(X, y)
    end = time.time()
    print("linear svc train time: ", (end-start))
    start = time.time()
    svr = svm.SVR(gamma="scale", max_iter=1000).fit(X, y)
    end = time.time()
    print("svr train time: ", (end-start))

    # create a mesh to plot in
    x_min, x_max = X[:, 0].min() - 1, X[:, 0].max() + 1
    print("x_min", x_min, "x_max: ", x_max)
    y_min, y_max = X[:, 1].min() - 1, X[:, 1].max() + 1
    print("y_min: ", y_min, "y_max: ", y_max)
    xx, yy = np.meshgrid(np.arange(x_min, x_max, h),
                         np.arange(y_min, y_max, h))

    # print("xx: ", xx)
    # print("yy: ", yy)

    # title for the plots
    titles = ['SVC with linear kernel',
              'LinearSVC (linear kernel)',
              'SVC with RBF kernel',
              'SVC with polynomial (degree 3) kernel',
              'SVR']

    svms = (svc, lin_svc, rbf_svc, poly_svc, svr)
    for i, clf in enumerate(svms):
        print("plotting SVC object: ", i + 1, "/", len(svms))
        # Plot the decision boundary. For that, we will assign a color to each
        # point in the mesh [x_min, x_max]x[y_min, y_max].
        plt.subplot(3, 2, i + 1)
        plt.subplots_adjust(wspace=0.4, hspace=0.4)

        # print("xx.ravel(): ", xx.ravel())
        # print("X.shape[1]: ", xx.shape[1])
        Z = clf.predict(np.c_[xx.ravel(), yy.ravel()])

        # Put the result into a color plot
        Z = Z.reshape(xx.shape)
        plt.contourf(xx, yy, Z, cmap=plt.cm.coolwarm, alpha=0.8)

        # Plot also the training points
        plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.coolwarm)
        plt.xlim(xx.min(), xx.max())
        plt.ylim(yy.min(), yy.max())
        plt.xticks(())
        plt.yticks(())
        plt.title(titles[i])

    print("About to show plt")
    plt.show()


def test_train_split(x, labels, dates, ticker_data, proportion_to_train_on=0.85):

    train_cutoff = int(len(x) * proportion_to_train_on)

    train_ticker_data = ticker_data[:train_cutoff]
    test_ticker_data = ticker_data[train_cutoff:]
    test_dates = dates[train_cutoff:]
    train_dates = dates[:train_cutoff]
    x_test = x[train_cutoff:]
    y_test = labels[train_cutoff:]
    X = x[:train_cutoff]
    Y = labels[:train_cutoff]

    print("len(X)", len(X))
    print("len(x_test) = ", len(x_test))

    return X, Y, x_test, y_test, train_dates, test_dates, train_ticker_data, test_ticker_data


def test(clf, x_test, y_test, test_dates, test_ticker_data, verbose=False):
    true = 0
    false = 0
    real_drops = 0

    true_negative = 0
    true_postive = 0
    false_negative = 0
    false_positive = 0

    tp_history = []
    tn_history = []
    fp_history = []
    fn_history = []

    # print("len(ticker_data)", len(test_ticker_data))
    # print("len(x_test)", len(x_test))

    for i in range(len(x_test)):
        # print(x)
        # print("proper label = ", x[-1])
        res = clf.predict(np.array(x_test[i]).reshape(1, -1))[0]
        res_prob = clf.predict_proba(np.array(x_test[i]).reshape(1, -1))[0]

        if res == -1:
            res_prob = res_prob[0]
        else:
            res_prob = res_prob[1]

        if res_prob < 0.8 and res == 1:
            res = 0

        if y_test[i] == 1:
            real_drops += 1

        if res == 1 and y_test[i] == -1:
            false_positive += 1
            fp_history.append(res_prob)
        elif res == -1 and y_test[i] == 1:
            false_negative += 1
            fn_history.append(res_prob)
        elif res == -1 and y_test[i] == -1:
            tn_history.append(res_prob)
            true_negative += 1
        elif res == 1 and y_test[i] == 1:
            tp_history.append(res_prob)
            true_postive += 1

        if res == y_test[i]:
            true += 1
        else:
            false += 1

    if verbose == True:
        print()
        print()
        print()

        print("total real drops (in test data): ", real_drops)
        print("True: ", true)
        print("False: ", false)

        print("True Negatives:", true_negative)
        print("True Positive:", true_postive)
        print("False Negative:", false_negative)
        print("False Positive:", false_positive)

    tp_fn = true_postive + false_negative
    if true_postive != 0 and (tp_fn != 0):
        sensitivity = (true_postive / (tp_fn)) * 100
    else:
        sensitivity = 0

    fp_tv = false_positive + true_negative
    if true_negative != 0 and fp_tv != 0:
        specificity = (true_negative / fp_tv) * 100
    else:
        specificity = 0

    tp_fp = true_postive + false_positive
    if tp_fp != 0:
        positive_predictive_value = (true_postive / (tp_fp)) * 100
    else:
        positive_predictive_value = 0

    fn_fp = false_negative + false_positive

    if fn_fp != 0:
        negative_predictive_value = (true_negative / (fn_fp)) * 100
    else:
        negative_predictive_value = 0
    if verbose == True:
        print()
        print()
        print()

        print("Sensitivity: ", sensitivity)
        print("Specificity: ", specificity)
        print("Positive Predictive Value: ", positive_predictive_value)
        print("Negative Predictive Value: ", negative_predictive_value)

        print("Overall Accuracy(the ability to ignore non-threshold moves and predict threshold moves): ",
              (true / (true + false)))

    fn_history = np.array(fn_history)
    tn_history = np.array(tn_history)
    fp_history = np.array(fp_history)
    tp_history = np.array(tp_history)

    print("fn_history std_dev:", fn_history.std())
    print("fn_history mean:", fn_history.mean())

    print("tn_history std_dev:", tn_history.std())
    print("tn_history mean:", tn_history.mean())

    print("fp_history std_dev:", fp_history.std())
    print("fp_history mean:", fp_history.mean())

    print("tp_history std_dev:", tp_history.std())
    print("tp_history mean:", tp_history.mean())

    return true, false, real_drops, true_negative, true_postive, false_negative, false_positive, sensitivity, specificity, positive_predictive_value, negative_predictive_value


def binary_search(arr, l, r, key_index, key):
    while l <= r:

        mid = int(l + (r - l)/2)

        # Check if x is present at mid
        if arr[mid][key_index] == key:
            return mid

        elif arr[mid][key_index] < key:
            l = mid + 1

        else:
            r = mid - 1

    return -1


def get_price_delta_distribution(ticker, year="ALL", verbose=False):
    price_deltas = []
    time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                              ticker + "_TIME_SERIES_DAILYData.csv")

    last_price = None
    for series_data in time_series[::-1].iterrows():
        if last_price == None:
            last_price = float(series_data[1][2])
            continue

        # print(series_data)

        curr_price = float(series_data[1][2])
        delta = ((curr_price - last_price) / last_price) * 100
        last_price = curr_price
        price_deltas.append(delta)

    if verbose == True:
        plot_histo(price_deltas, "% Price Moves " + ticker, "% Move",)

    return price_deltas


def get_rsi_threshold_move_distribution(tickers, year, rsi_threshold, days_from_inversion=1, verbose=False):
    history = []
    price_deltas = []
    volumes = []

    for ticker in tickers:
        days_above_threshold = 0

        data = load_technical_data(ticker, "RSI", "14dailyclose")

        time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                                  ticker + "_TIME_SERIES_DAILYData.csv")
        prices = []
        volumes = []

        prev_date = ""
        days_from_invesrion_counter = 0

        earliest_date = get_earliest_date(ticker, ["RSI"], "14dailyclose")
        latest_date = get_latest_date(ticker, ["RSI"], "14dailyclose")
        last_rsi = ""
        iteration_count = 0

        for series_data in time_series[::-1].iterrows():
            # print(series_data)
            prices.append(float(series_data[1][2]))
            volumes.append(float(series_data[1][5]))

        for i in range(len(data)):
            date = data[i][0]
            # print(data)
            iteration_count += 1
            if data[i][0] < earliest_date:  # skip the first item so we don't get a 0 value
                prev_date = date.split(" ")[0]
                index = binary_search(data, 0, len(data), 0, date)
                if index != -1:
                    last_rsi = data[index][1]
                continue

            if data[i][0] > latest_date:
                break

            if date.split("-")[0] >= year or year == "ALL":
                # get the RSI value on the date parameter
                index = binary_search(data, 0, len(data), 0, date)
                if index != -1:

                    if index + days_from_inversion > len(data):
                        continue

                    if data[index-1][1] > rsi_threshold and data[index][1] < rsi_threshold:
                        # index - 1 == yesterday.
                        # index == today.
                        # if the RSI was above 70 yesterday and is below 70 today then record the price movement between today and today + days_from_inversion.

                        # print("Inversion from past ", rsi_threshold , prev_date, " to ", date, " was ", days_from_inversion_threshold, " long")

                        # print("prev_date = ", prev_date, "date = ", date)

                        price_delta = 100 * \
                            ((prices[index] - prices[index +
                                                     days_from_inversion]) / prices[index])

                        try:
                            # print(volumes[index-1])
                            one_day_ago_volume = volumes[index-1]
                            two_days_ago_volume = volumes[index-2]
                            three_days_ago_volume = volumes[index-3]

                            three_day_avg_volume = (
                                three_days_ago_volume + two_days_ago_volume + one_day_ago_volume) / 3

                            # print("pd: ", price_delta)
                            # print("days_above_threshold: ", days_above_threshold)
                            # print("three_day_avg_volume: ", three_day_avg_volume)
                            price_deltas.append(price_delta)
                            history.append(days_above_threshold)
                            volumes.append(three_day_avg_volume)
                        except:
                            print()

                        days_above_threshold = 0
                        days_from_invesrion_counter = 0
            prev_date = date.split(" ")[0]

    # Setting all these to 0 so returning them doesn't throw an error since sometimes the arrays used to determine them are empty.
    history = np.array(history)
    price_deltas = np.array(price_deltas)
    volumes = np.array(volumes)
    history_std_dev = 0
    history_mean = 0
    price_delta_std_dev = 0
    price_delta_mean = 0
    volumes_mean = 0
    volumes_std_dev = 0
    corr_matrix = [0]
    if len(history) > 0 and len(price_deltas) > 0 and len(volumes) > 0:
        history_std_dev = history.std()
        history_mean = history.mean()
        price_delta_std_dev = price_deltas.std()
        price_delta_mean = price_deltas.mean()
        volumes_mean = volumes.mean()
        volumes_std_dev = volumes.std()

        amalgamation = amalgamate_data(history, price_deltas, volumes)
        corr_matrix = np.corrcoef(history, price_deltas)

        if verbose == True:
            print("latest date: ", latest_date)
            print("earliest date: ", earliest_date)
            print("len(history):", len(history))
            print("len(price_deltas):", len(price_deltas))

            print("Std_Dev of days above threshold:", history_std_dev)

            print("Mean Days Above " + str(rsi_threshold) + ":", history_mean)

            print("Std_Dev Of Price Deltas:", price_delta_std_dev)

            print("Mean of Price Deltas:", price_delta_mean)
            print("corrletation of the length of > " + str(rsi_threshold) +
                  " RSI to returns")
            print(corr_matrix)

            plot_histo(history, "Length of periods of time RSI > " +
                       str(rsi_threshold), "length of period", figure=1)

            plot_histo(price_deltas, 'Histogram of Price Deltas ' + str(days_from_inversion) + " days from inversion",
                       '%Price Change', figure=2)

            plot_scatter(history, price_deltas, "length of oversold period", "% price move",
                         title="Length of Period & Percent Move in That Period", figure=3)

            plot_correlation_matrix(
                corr_matrix, ["len(period)", "%price change", "volume"], figure=4)
            plt.show()

    return history, history_std_dev, history_mean, price_deltas, price_delta_std_dev, price_delta_mean, volumes, volumes_mean, corr_matrix


def get_optimal_rsi_days_from_inversion(ticker, year="2019", rsi_threshold=70, verbose=False):
    max_corr = 0
    best_threshold = 0
    # intuition tells me theres no way that an rsi inversion is even remotely relevant > 14 days later.
    for i in range(0, 14):
        _, _, _, price_deltas, _, _, _, _ = get_rsi_threshold_move_distribution(
            ticker, year, rsi_threshold, i, False)
        corr = np.corrcoef(i, price_deltas)
        if abs(corr[0][0]) > max_corr:
            max_corr = abs(corr[0][0])
            best_threshold = i
    print("Best Correlation(", max_corr,
          ") was for Days From Inversion = ", best_threshold)
    return best_threshold


def get_optimal_rsi_threshold(ticker, year="ALL", days_from_inversion_threshold=1, verbose=False):
    pd_mean_history = []
    threshold_history = []
    dispersion_history = []
    # only 100 possible values for the RSI indicator so thats all we need to loop for. Starting at 10 because the rsi is never at 0.
    for i in range(10, 100):
        _, _, _, _, price_delta_std_dev, price_delta_mean, _, _, _ = get_rsi_threshold_move_distribution(
            [ticker], year, i, days_from_inversion_threshold, False)
        variance = price_delta_std_dev ** 2

        if variance != 0 and price_delta_mean != 0:
            dispersion_history.append((variance / price_delta_mean))
        else:
            dispersion_history.append(0)
        threshold_history.append(i)
        pd_mean_history.append(price_delta_mean)

    plot_scatter(threshold_history, pd_mean_history,
                 "RSI Threshold", "Mean % Move", 1)
    plot_scatter(threshold_history, dispersion_history, "RSI Threshold",
                 "Dispersion Index of % Moves", "Dispersion Indexes", 2)
    corr = np.corrcoef(amalgamate_data(
        pd_mean_history, dispersion_history, threshold_history))
    plot_correlation_matrix(
        corr, ["Price Delta History", "Dispersion History", "Threshold History"], 3)
    print("Correlation: ", corr)
    plt.show()


def plot_correlation_matrix(corr, labels, figure=1):
    plt.figure(figure)
    sns.heatmap(corr,
                xticklabels=labels,
                yticklabels=labels)


def plot_histo(arr, title, x_label, figure=1):
    # matplotlib histogram
    plt.figure(figure)
    plt.hist(arr, color='blue', edgecolor='black',
             bins=int(180/5))

    # seaborn histogram
    sns.distplot(arr, hist=True, kde=False,
                 bins=int(180/5), color='blue',
                 hist_kws={'edgecolor': 'black'})

    mean = np.array(arr).mean()
    std_dev = np.array(arr).std()
    decimal_places = 3
    plt.legend(["Std Dev: {0:.3f}".format(round(std_dev, decimal_places)), "N: {0:.3f}".format(
        len(arr)), "Mean: {0:.3f}".format(round(mean, decimal_places))], loc=2)
    # Add labels
    plt.title(title)
    plt.xlabel(x_label)
    plt.ylabel('# Occurances')


def plot_scatter(x, y, x_label, y_label, title, figure=1):
    plt.figure(figure)
    plt.title(title)
    plt.scatter(x, y, color='r')
    plt.xlabel(x_label)
    plt.ylabel(y_label)
    plt.title('len(oversold) to % move scatter plot')

# series_types = ["close", "open", "high", "low"]
# technicals = ["RSI"]
# time_periods = ["14"]
# time_intervals = ["5min"]
# tickers = ["AMD"]
# rows_and_cols = sqrt(len(series_type) * len(technicals) * len(time_periods) * len(time_intervals) * len(tickers)) + 1
# i = 0
# for ticker in tickers:
#     for s in series_types:
#         for t in technicals:
#             for tp in time_periods:
#                 for i in time_intervals:
#                     plt.subplot(rows_and_cols, rows_and_cols, i)
#                     i ++
#                     get_rsi_threshold_move_distribution(ticker, "2019")

# vals = history, history_std_dev, history_mean, price_deltas, price_delta_std_dev, price_delta_mean, volumes, volumes_mean, corr_matrix


def test_many_scenarios(tickers, rsi_start, rsi_end, days_from_inv_range):
    figure = 1
    subplot_index = 1
    scenario_results = []
    records = []
    futures = []
    for i in range(rsi_start, rsi_end + 1):  # n different RSI thresholds
        # m different inversion thresholds, we don't need a start for these because it
        for j in range(1, days_from_inv_range + 1):

            with concurrent.futures.ThreadPoolExecutor() as executor:
                future = executor.submit(
                    get_rsi_threshold_move_distribution, tickers, "ALL",  1 + i, j, False)
                futures.append(future)
                moves_distribution = future.result()[3]

                # print("sub", subplot_index)
                plt.subplot(5, 5, subplot_index)

                # shapiro test requires a length of at least 3.
                if len(moves_distribution) < 3:
                    continue

                # print("RSI = ", str(i), "dI = ", str(j), "W = ", w, " P = ", p)
                plot_histo(moves_distribution, "RSI = " + str(i) +
                           "dI = " + str(j), "% Move", figure)

                if subplot_index % 25 == 0:
                    figure += 1
                    plt.figure(figure)
                    subplot_index = 0
                subplot_index += 1

    return scenario_results


def sample_randomly(data, n_samples):
    seed(1)
    sample = []
    for i in range(n_samples):
        index = int(random() * len(data))
        sample.append(data[index])
    return sample


def get_next_day_correlation(ticker, threshold=0, verbose=False):
    time_series = pd.read_csv("C:/Users/trist/Desktop/Projects/Stock_Data/" +
                              ticker + "_TIME_SERIES_DAILYData.csv")
    prices = []

    for series_data in time_series[::-1].iterrows():
            move = (float(series_data[1][1]) - float(series_data[1][4])) / float(series_data[1][1])
            prices.append(move)
            

    curr_day_moves = []
    next_day_moves = []
    
    i = 0
    while i < len(prices) - 1:
        if abs(prices[i]) >= threshold:
            curr_day_moves.append(abs(prices[i]))
            next_day_moves.append(abs(prices[i + 1]))
        i += 2


    corr_matrix = np.corrcoef(curr_day_moves, next_day_moves)

    #TODO: transform into 2D array so we can regess it
    # reg = LinearRegression().fit(curr_day_moves, next_day_moves)

    if verbose == True:
        plot_scatter(curr_day_moves, next_day_moves, "todays move", "tommorows move", "Correlation between todays move and tommorows move", 2)
        # plot_correlation_matrix(corr_matrix, ["move at t", "move at t + 1"], 3)
        plot_histo(next_day_moves, "Moves @ t + 1", "% Move", 4)
        print(corr_matrix)
        # print("Regression Score", reg.score(curr_day_moves, next_day_moves))
    return corr_matrix, reg
vix_moves = get_price_delta_distribution("VIX", verbose=True)
get_next_day_correlation("VIX", 0.12, True)
plt.show()
exit(1)

# rsi_start = 74
# rsi_end = 76

# test_many_scenarios(["AMD"], rsi_start, rsi_end, 7)

# plt.show()
# exit(1)

all_price_deltas = get_price_delta_distribution("AMD", verbose=False)
sub_sample = sample_randomly(all_price_deltas, 54)
plot_histo(all_price_deltas, "All Moves", "% Moves", 1)
plot_histo(sub_sample, "Subsample Moves", "% Moves", 2)
_, _, _, rsi_price_deltas, _, _, _, _, _ = get_rsi_threshold_move_distribution([
                                                                               "AMD"], "ALL", 75, 1)
plot_histo(rsi_price_deltas, "RSI Crosses Below 75 Moves", "% Moves", 3)

t_test_t, t_test_p = st.ttest_ind(all_price_deltas, rsi_price_deltas)

# keep the sample size below 5,000 to avoid p-value warning
shapiro_w1, shapiro_p1 = st.shapiro(all_price_deltas[0:4000])
shapiro_w2, shapiro_p2 = st.shapiro(rsi_price_deltas)

print("Population_N: ", len(all_price_deltas),
      "Sample_N: ", len(rsi_price_deltas))
print("Shapiro Test Results")
print("Population W: ", shapiro_w1, " P: ", shapiro_p1)
print("Sample     W: ", shapiro_w2, " P: ", shapiro_p2)

print("T Test Results")
print("t = ", t_test_t)
print("p = ", t_test_p)

plt.show()

# get_optimal_rsi_threshold("AMD")

# get_optimal_rsi_days_from_inversion("AMD")

exit(1)

decision_tree = DecisionTreeClassifier(
    criterion="entropy", min_samples_split=20, random_state=99)
guassian = GaussianNB()
random_forest = RandomForestClassifier(
    n_estimators=10)  # BEST FOR SPY ===> 75%
knn_bagger = BaggingClassifier(KNeighborsClassifier(
), max_samples=0.2, max_features=0.2, n_estimators=15)
tree_bagger = BaggingClassifier(DecisionTreeClassifier(
    criterion="entropy", min_samples_split=20, random_state=99), max_samples=1.0, n_estimators=15)

models = [decision_tree, guassian, random_forest, knn_bagger, tree_bagger]

###########################################################
train_tickers = ["ACB", "BKNG", "BIDU", "CGC", "CSIQ", "GRUB",
                 "MSFT", "ROKU", "SHOP", "TSLA", "TWLO", "WIX", "ZBRA"]
# technicals = ["ADX", "ATR", "AROONOSC", "SAR", "RSI", "ROCR", "MFI", "TRIX", "PPO"]
# TODO: Change the periods used in the PPO technical to reflect my short term trading, maybe 1, 2, 3 day or 1, 3, 5?
technicals = ["ADX", "ATR", "AROONOSC", "SAR",
              "RSI", "ROCR", "MFI", "TRIX", "PPO"]

x, labels, dates, ticker_data = get_data(
    train_tickers, technicals, time_period=5, std_dev_threshold=-2, noise_ratio=4)
X, Y, _, _, train_dates, _, _, _ = test_train_split(
    x, labels, dates, ticker_data, 1)

test_tickers = ["APH", "APHA"]
x_test, y_test, test_dates, test_ticker_data = get_data(
    test_tickers, technicals, time_period=5, std_dev_threshold=-1.5, noise_ratio=4)

print("First/Last Test Date: ", test_dates[0], test_dates[-1])
print("Training on ", train_tickers, " data")
print("Testing on ", test_tickers, " data")

for clf in models:
    clf.fit(X, Y)
    # print("Feature Importance: ", clf.feature_importances_)
    # the line below tests the model and returns a BUNCH of testing values, hence the line being so big.
    print()
    print("Test Values For: ", clf.__class__)
    true, false, real_drops, true_negative, true_positive, false_negative, false_positive, sensitivity, specificity, positive_predictive_value, negative_predictive_value = test(
        clf, x_test, y_test, test_dates, test_ticker_data, verbose=False)
    print("Sensitivity ", sensitivity)
    print("Real Drops ", real_drops)
    print("True Positives (Profitable Trades)", true_positive)
    print("False Positives (Incorrect Predictions, Lost Money)", false_positive)
    print("Profitable Trades/Money Loosers",
          ((true_positive + 0.0000000001)/(false_positive + 0.0000000001)))
    print("False Negatives (Opportunity Cost)", false_negative)
###########################################################
