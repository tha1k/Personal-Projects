#%%
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt
from collections import Counter 
from sklearn.metrics import accuracy_score
from sklearn.metrics import recall_score
from sklearn.metrics import precision_score
from sklearn.metrics import f1_score
from sklearn.metrics import confusion_matrix


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
(x_train_imdb, y_train_imdb), (x_test_imdb, y_test_imdb) = tf.keras.datasets.imdb.load_data()
#create a custom vocabulary
voc = create_vocabulary(reviews=x_train_imdb ,max_words=M , exclude_least_common=k, exclude_most_common=n)


word_index = tf.keras.datasets.imdb.get_word_index()

# Create a reverse word index
index2word = {i + 3: word for word, i in word_index.items()}
index2word[0] = '[pad]'
index2word[1] = '[bos]'
index2word[2] = '[oov]'

# Convert numerical sequences back to text
x_train_imdb_text = [' '.join(index2word.get(idx, '[oov]') for idx in text) for text in x_train_imdb]
x_test_imdb_text = [' '.join(index2word.get(idx, '[oov]') for idx in text) for text in x_test_imdb]

# Convert to numpy arrays
x_train_imdb_text = np.array(x_train_imdb_text)
x_test_imdb_text = np.array(x_test_imdb_text)



train_scores=[]
examples_used=[]
test_sizes= [ 0.1 , 0.25,0.5, 0.75 , 0.99]
test_scores = []
test_recall = []
test_precision_score=[]
test_Fscore =[]
test_confusion_matrices=[]
vectorizer = CountVectorizer(binary=True, vocabulary=voc)

for size in test_sizes:
    x_train, x_dev, y_train, y_dev = train_test_split(x_train_imdb_text, y_train_imdb, test_size=1-size , random_state=42)
    data = np.shape(x_train)[0]
    examples_used.append(data)
    
    X_train_selected_vectorized = vectorizer.transform(x_train)
    X_dev_selected_vectorized = vectorizer.transform(x_dev)
    sk_forest = RandomForestClassifier()
    sk_forest.fit(X_train_selected_vectorized, y_train)
    train_pred = sk_forest.predict(X_train_selected_vectorized)
    train_accuracy = accuracy_score(y_train , train_pred)
    train_scores.append(train_accuracy)
    x_test_vectorized = vectorizer.transform(x_test_imdb_text)
    predictions = sk_forest.predict(x_test_vectorized)
    accuracy = accuracy_score(y_test_imdb, predictions)
    recall = recall_score(y_test_imdb, predictions)
    precision = precision_score(y_test_imdb, predictions)
    f1 = f1_score(y_test_imdb, predictions)
    conf_matrix = confusion_matrix(y_test_imdb, predictions)
    test_Fscore.append(f1)
    test_precision_score.append(precision)
    test_recall.append(recall)
    test_confusion_matrices.append(conf_matrix)
    test_scores.append(accuracy)

print(test_Fscore)

fig , axs = plt.subplots(2, 2 , figsize=(15, 10))
axs[0, 0].plot(examples_used, test_scores, marker='o', linestyle='-', color='green')
axs[0, 0].plot(examples_used, train_scores, marker='o', linestyle='-', color='red')
axs[0, 0].set_title('Error')
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

fig.suptitle('Scikit-learn RandomForest with 5000 word vocabulary exluding 800 most common words', fontsize=16)

plt.tight_layout()
plt.show()
