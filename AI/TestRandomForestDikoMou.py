#%%
from sklearn.model_selection import train_test_split
import numpy as np
import tensorflow as tf
from RandomForest import RandomForest
from collections import Counter 
import matplotlib.pyplot as plt 
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.metrics import precision_score
from sklearn.metrics import f1_score
from sklearn.metrics import confusion_matrix
from sklearn.ensemble import RandomForestClassifier
from sklearn.feature_extraction.text import CountVectorizer


def create_vocabulary(reviews, max_words, exclude_least_common, exclude_most_common) -> dict:
    word_list = []
    for review in reviews:
        for word in review:
            word_list.append(word)

    vocabulary = {0: '[pad]', 1: '[bos]', 2: '[oov]'}

    word_counter_dict = Counter(word_list)
    
    sorted_word_count = word_counter_dict.most_common() #word_index: times seen
    last = len(sorted_word_count) - exclude_least_common
    voc_words = sorted_word_count[exclude_most_common: last ]
    voc_words = [word[0] for word in voc_words]
    
    for i in range(max_words):
        vocabulary[i+3] = voc_words[i]
    # Replace the following line with your actual implementation of getIndexToWord
    for i in range(3, len(vocabulary)):
        vocabulary[i] = getIndexToWord(vocabulary[i])

    inv_map = {v: k for k, v in vocabulary.items()}
    return inv_map  

def getIndexToWord(index):
    word_index = tf.keras.datasets.imdb.get_word_index()

# Invert the word index to get a dictionary mapping indices to words
    index_to_word = {idx: word for word, idx in word_index.items()}

# Use the index to get the corresponding word
    your_word = index_to_word.get(index, '[oov]')

    return your_word

def preprocess_review(reviews, vocabulary):
    num_reviews = len(reviews)
    num_features = len(vocabulary)
    processed_data = np.zeros((num_reviews, num_features), dtype=int)
    
    for i, review in enumerate(reviews):
        vectorized_review = np.zeros(num_features)
        words = review.split()
        for word in words:  # reviews are strings and need to be split into words
            word_index = vocabulary.get(word, -1)  # Get the index from the vocabulary or -1 if it is not found
            if word_index != -1 and word_index!=1:
                vectorized_review[word_index] = 1
            else:
                continue

        processed_data[i] = vectorized_review

    return processed_data

M=5000#words used 
n=800 #ignored
k=800 # ignored
(X_train_imdb, y_train_imdb), (X_test_imdb, y_test_imdb) = tf.keras.datasets.imdb.load_data()
#create a custom vocabulary
voc = create_vocabulary(reviews=X_train_imdb ,max_words=M , exclude_least_common=k, exclude_most_common=n)

word_index = tf.keras.datasets.imdb.get_word_index()

# Create a reverse word index
index2word = {i + 3: word for word, i in word_index.items()}
index2word[0] = '[pad]'
index2word[1] = '[bos]'
index2word[2] = '[oov]'

# Convert numerical sequences back to text
X_train_imdb_text = [' '.join(index2word.get(idx, '[oov]') for idx in text) for text in X_train_imdb]
X_test_imdb_text = [' '.join(index2word.get(idx, '[oov]') for idx in text) for text in X_test_imdb]

# Convert to numpy arrays
X_train_imdb_text = np.array(X_train_imdb_text)
X_test_imdb_text = np.array(X_test_imdb_text)

processed_train_data = preprocess_review(X_train_imdb_text, voc)
processed_test_data = preprocess_review(X_test_imdb_text, voc)
train_sizes= [0.05, 0.25,  0.5, 0.75 , 0.99]
examples_used=[]
test_scores = []
test_recall = []
test_precision_score=[]
test_Fscore =[]
test_confusion_matrices=[]
train_scores=[]


for size in train_sizes:
    X_train, X_dev, y_train, y_dev = train_test_split(processed_train_data, y_train_imdb, test_size = 1-size, random_state=42)

    prob_positive_rev = np.sum(y_train)/ np.shape(y_train)
    data = np.shape(X_train)[0]
    examples_used.append(data)

    forest = RandomForest()
    forest.fit(X_train, y_train)
    train_predict = forest.predict(X_train)
    train_data_accuracy = accuracy_score(y_train, train_predict)
    train_scores.append(train_data_accuracy)  

    predictions = forest.predict(processed_test_data)
    acc = accuracy_score(y_test_imdb, predictions)
    recall = recall_score(y_test_imdb, predictions)
    precision = precision_score(y_test_imdb, predictions)
    f1 = f1_score(y_test_imdb, predictions)
    conf_matrix = confusion_matrix(y_test_imdb, predictions)
    test_Fscore.append(f1)
    test_precision_score.append(precision)
    test_recall.append(recall)
    test_scores.append(acc)
    test_confusion_matrices.append(conf_matrix)
   
print("ok")
print(test_confusion_matrices)


coin_flip = [0.5 for _ in range(len(train_scores))]

fig , axs = plt.subplots(2, 2 , figsize=(15, 10))
axs[0, 0].plot(examples_used, test_scores, marker='o', linestyle='-', color='green', label='test data')
axs[0, 0].plot(examples_used, train_scores, marker='o', linestyle='-', color='red', label= 'train dataset')
axs[0,0].plot(examples_used,coin_flip, linestyle='--', color = "blue" )
axs[0, 0].set_title('Accuracy')
axs[0, 0].set_xlabel('Test Size')
axs[0, 0].set_ylabel('Accuracy')



axs[0, 1].plot(examples_used, test_precision_score, marker='o', linestyle='-', color='green')
axs[0, 1].set_title('Precision')
axs[0, 1].set_xlabel('Test Size')
axs[0, 1].set_ylabel('Precision')

axs[1, 0].plot(examples_used, test_recall, marker='o', linestyle='-', color='green')
axs[1, 0].set_title('Recall')
axs[1, 0].set_xlabel('Test Size')
axs[1, 0].set_ylabel('Recall')


#f score is calculated with b=1 
axs[1, 1].plot(examples_used, test_Fscore, marker='o', linestyle='-', color='green')
axs[1, 1].set_title('F1 Score')
axs[1, 1].set_xlabel('Test Size')
axs[1, 1].set_ylabel('F1 Score')


fig.suptitle('5000 word vocabulary exluding 800 most common words', fontsize=16)

plt.tight_layout()
plt.show()

######################################################################################################
#sklearn random forest
