Storing keys in a binary search tree will need time proportional to O (M*log N), where M is maximum string length and N is no. of keys in tree. Using trie we can search the key in O(M) time. However the penalty is the trie storing requirements. 
Adding a word into a trie is a O(k), k is the length of key & space is O(26 * k *N) where n is number of keys. 
EXTRA CRED:
Implemented reading straight from the github raw URL.
Implemented Levenshtein Distance formula as well as a famous algorithm called soundex. 
https://en.wikipedia.org/wiki/Soundex
I implemented Good Spell Checking and Active Spell Checking within my processInputFile.java method located in my spellCorrector class. 
