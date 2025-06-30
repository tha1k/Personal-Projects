import numpy as np
class Naive_Bayes:
    def __init__(self, positive_review_probability, vocabulary) -> None:
        self.features_probs_positive = np.ones(len(vocabulary), dtype=np.float64)
        self.features_probs_negative = np.ones(len(vocabulary), dtype=np.float64)
        self.positive_review_prob = positive_review_probability
        self.negative_review_prob = 1 - positive_review_probability

    def fit(self, X, y):
        for j in range(np.shape(self.features_probs_positive)[0]):
            self.features_probs_negative[j], self.features_probs_positive[j] = self.calculate_feature_posterior_prob(X, y, j)

    def predict(self, X):
        predictions = []

        for review in X:
            # Calculate P(C=1 | X) and P(C=0 | X) using the stored probabilities
            prob_positive_X = self.positive_review_prob * np.prod([self.features_probs_positive[j] for j in range(len(review)) if review[j] == 1])
            prob_negative_X = self.negative_review_prob * np.prod([self.features_probs_negative[j] for j in range(len(review)) if review[j] == 1])

            # Make a prediction based on which probability is higher
            if prob_negative_X >= prob_positive_X:
                predictions.append(0)
            else:
                predictions.append(1)
        return predictions

    def calculate_feature_posterior_prob(self, X, y, j):
        positive_counts = 0
        negative_counts = 0
        neg = sum(1 for sentiment in y if sentiment == 0) #P(c=0)
        pos = sum(1 for sentiment in y if sentiment == 1)

        for review, sentiment in zip(X, y):
            if review[j] == 1 and sentiment == 1:
                positive_counts += 1
            elif review[j] == 1 and sentiment == 0:
                negative_counts += 1

        pos_prob = (1 + positive_counts) / (pos + 2)  # Laplace smoothing
        neg_prob = (1 + negative_counts) / (neg + 2)
        return neg_prob, pos_prob