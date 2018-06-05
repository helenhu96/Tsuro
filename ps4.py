import numpy as np

def cosine_similarity(a, b):
    return np.dot(a, b) * 1.0 / np.linalg.norm(a)/ np.linalg.norm(b)