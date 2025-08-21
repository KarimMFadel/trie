package com.tornado.trie;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive unit tests for the Trie data structure.
 */
class TrieTest {

    private TrieDS trie;

    @BeforeEach
    void setUp() {
        trie = new TrieDS();
    }

    @Nested
    @DisplayName("Create Operations")
    class CreateOperations {

        @Test
        @DisplayName("Should create empty trie")
        void shouldCreateEmptyTrie() {
            assertTrue(trie.isEmpty());
        }
    }

    @Nested
    @DisplayName("Insert Operations")
    class InsertOperations {

        @Test
        @DisplayName("Should insert single word")
        void shouldInsertSingleWord() {
            trie.insert("cat");
            assertTrue(trie.lookup("cat"));
            assertFalse(trie.isEmpty());
        }

        @Test
        @DisplayName("Should insert word with value")
        void shouldInsertWordWithValue() {
            trie.insert("dog", "canine");
            assertTrue(trie.lookup("dog"));
            assertEquals("canine", trie.getValue("dog"));
        }

        @Test
        @DisplayName("Should insert multiple words")
        void shouldInsertMultipleWords() {
            trie.insert("cat");
            trie.insert("car");
            trie.insert("card");

            assertTrue(trie.lookup("cat"));
            assertTrue(trie.lookup("car"));
            assertTrue(trie.lookup("card"));
        }

        @Test
        @DisplayName("Should handle overlapping words")
        void shouldHandleOverlappingWords() {
            trie.insert("car");
            trie.insert("card");
            trie.insert("care");

            assertTrue(trie.lookup("car"));
            assertTrue(trie.lookup("card"));
            assertTrue(trie.lookup("care"));
            assertFalse(trie.lookup("ca"));
        }

        @Test
        @DisplayName("Should throw exception for null word")
        void shouldThrowExceptionForNullWord() {
            assertThrows(IllegalArgumentException.class, () -> trie.insert(null));
        }

        @Test
        @DisplayName("Should throw exception for empty word")
        void shouldThrowExceptionForEmptyWord() {
            assertThrows(IllegalArgumentException.class, () -> trie.insert(""));
        }

        @Test
        @DisplayName("Should handle single character words")
        void shouldHandleSingleCharacterWords() {
            trie.insert("a");
            trie.insert("b");

            assertTrue(trie.lookup("a"));
            assertTrue(trie.lookup("b"));
        }
    }

    @Nested
    @DisplayName("Lookup Operations")
    class LookupOperations {

        @BeforeEach
        void setUpWords() {
            trie.insert("cat", "feline");
            trie.insert("car");
            trie.insert("card");
        }

        @Test
        @DisplayName("Should find existing words")
        void shouldFindExistingWords() {
            assertTrue(trie.lookup("cat"));
            assertTrue(trie.lookup("car"));
            assertTrue(trie.lookup("card"));
        }

        @Test
        @DisplayName("Should not find non-existing words")
        void shouldNotFindNonExistingWords() {
            assertFalse(trie.lookup("dog"));
            assertFalse(trie.lookup("ca"));
            assertFalse(trie.lookup("cards"));
        }

        @Test
        @DisplayName("Should get correct values")
        void shouldGetCorrectValues() {
            assertEquals("feline", trie.getValue("cat"));
            assertNull(trie.getValue("car"));
            assertNull(trie.getValue("dog"));
        }

        @Test
        @DisplayName("Should throw exception for null lookup")
        void shouldThrowExceptionForNullLookup() {
            assertThrows(IllegalArgumentException.class, () -> trie.lookup(null));
            assertThrows(IllegalArgumentException.class, () -> trie.getValue(null));
        }

        @Test
        @DisplayName("Should throw exception for empty lookup")
        void shouldThrowExceptionForEmptyLookup() {
            assertThrows(IllegalArgumentException.class, () -> trie.lookup(""));
            assertThrows(IllegalArgumentException.class, () -> trie.getValue(""));
        }
    }

    @Nested
    @DisplayName("Update Operations")
    class UpdateOperations {

        @BeforeEach
        void setUpWords() {
            trie.insert("cat", "feline");
            trie.insert("dog");
        }

        @Test
        @DisplayName("Should update existing word with value")
        void shouldUpdateExistingWordWithValue() {
            assertTrue(trie.update("cat", "domestic feline"));
            assertEquals("domestic feline", trie.getValue("cat"));
        }

        @Test
        @DisplayName("Should update existing word without value")
        void shouldUpdateExistingWordWithoutValue() {
            assertTrue(trie.update("dog", "canine"));
            assertEquals("canine", trie.getValue("dog"));
        }

        @Test
        @DisplayName("Should not update non-existing word")
        void shouldNotUpdateNonExistingWord() {
            assertFalse(trie.update("elephant", "large mammal"));
        }

        @Test
        @DisplayName("Should update value to null")
        void shouldUpdateValueToNull() {
            assertTrue(trie.update("cat", null));
            assertNull(trie.getValue("cat"));
            assertTrue(trie.lookup("cat")); // Word should still exist
        }

        @Test
        @DisplayName("Should throw exception for null word update")
        void shouldThrowExceptionForNullWordUpdate() {
            assertThrows(IllegalArgumentException.class, () -> trie.update(null, "value"));
        }

        @Test
        @DisplayName("Should throw exception for empty word update")
        void shouldThrowExceptionForEmptyWordUpdate() {
            assertThrows(IllegalArgumentException.class, () -> trie.update("", "value"));
        }
    }

    @Nested
    @DisplayName("Delete Operations")
    class DeleteOperations {

        @BeforeEach
        void setUpWords() {
            trie.insert("cat");
            trie.insert("car");
            trie.insert("card");
            trie.insert("care");
            trie.insert("careful");
        }

        @Test
        @DisplayName("Should delete existing word")
        void shouldDeleteExistingWord() {
            assertTrue(trie.delete("cat"));
            assertFalse(trie.lookup("cat"));
        }

        @Test
        @DisplayName("Should not delete non-existing word")
        void shouldNotDeleteNonExistingWord() {
            assertFalse(trie.delete("dog"));
        }

        @Test
        @DisplayName("Should delete word without affecting others")
        void shouldDeleteWordWithoutAffectingOthers() {
            assertTrue(trie.delete("careful"));

            assertFalse(trie.lookup("careful"));
            assertTrue(trie.lookup("care"));
            assertTrue(trie.lookup("car"));
            assertTrue(trie.lookup("card"));
        }

        @Test
        @DisplayName("Should delete word that is prefix of another")
        void shouldDeleteWordThatIsPrefixOfAnother() {
            assertTrue(trie.delete("care"));

            assertFalse(trie.lookup("care"));
            assertTrue(trie.lookup("careful"));
            assertTrue(trie.lookup("car"));
        }

        @Test
        @DisplayName("Should delete all words to make trie empty")
        void shouldDeleteAllWordsToMakeTrieEmpty() {
            assertTrue(trie.delete("cat"));
            assertTrue(trie.delete("car"));
            assertTrue(trie.delete("card"));
            assertTrue(trie.delete("care"));
            assertTrue(trie.delete("careful"));

            assertTrue(trie.isEmpty());
        }

        @Test
        @DisplayName("Should throw exception for null word delete")
        void shouldThrowExceptionForNullWordDelete() {
            assertThrows(IllegalArgumentException.class, () -> trie.delete(null));
        }

        @Test
        @DisplayName("Should throw exception for empty word delete")
        void shouldThrowExceptionForEmptyWordDelete() {
            assertThrows(IllegalArgumentException.class, () -> trie.delete(""));
        }
    }

    @Nested
    @DisplayName("Prefix Search Operations")
    class PrefixSearchOperations {

        @BeforeEach
        void setUpWords() {
            trie.insert("cat");
            trie.insert("car");
            trie.insert("card");
            trie.insert("care");
            trie.insert("careful");
            trie.insert("dog");
            trie.insert("dodge");
        }

        @Test
        @DisplayName("Should find words with prefix 'car'")
        void shouldFindWordsWithPrefixCar() {
            List<String> words = trie.getWordsWithPrefix("car");
            assertEquals(4, words.size());
            assertTrue(words.contains("car"));
            assertTrue(words.contains("card"));
            assertTrue(words.contains("care"));
            assertTrue(words.contains("careful"));
        }

        @Test
        @DisplayName("Should find words with prefix 'do'")
        void shouldFindWordsWithPrefixDo() {
            List<String> words = trie.getWordsWithPrefix("do");
            assertEquals(2, words.size());
            assertTrue(words.contains("dog"));
            assertTrue(words.contains("dodge"));
        }

        @Test
        @DisplayName("Should return empty list for non-existing prefix")
        void shouldReturnEmptyListForNonExistingPrefix() {
            List<String> words = trie.getWordsWithPrefix("elephant");
            assertTrue(words.isEmpty());
        }

        @Test
        @DisplayName("Should handle empty prefix")
        void shouldHandleEmptyPrefix() {
            List<String> words = trie.getWordsWithPrefix("");
            assertEquals(7, words.size()); // All words in the trie
        }

        @Test
        @DisplayName("Should throw exception for null prefix")
        void shouldThrowExceptionForNullPrefix() {
            assertThrows(IllegalArgumentException.class, () -> trie.getWordsWithPrefix(null));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        @DisplayName("Should handle case sensitive words")
        void shouldHandleCaseSensitiveWords() {
            trie.insert("Cat");
            trie.insert("cat");

            assertTrue(trie.lookup("Cat"));
            assertTrue(trie.lookup("cat"));
            assertFalse(trie.lookup("CAT"));
        }

        @Test
        @DisplayName("Should handle special characters")
        void shouldHandleSpecialCharacters() {
            trie.insert("hello-world");
            trie.insert("hello_world");
            trie.insert("hello.world");

            assertTrue(trie.lookup("hello-world"));
            assertTrue(trie.lookup("hello_world"));
            assertTrue(trie.lookup("hello.world"));
        }

        @Test
        @DisplayName("Should handle numeric strings")
        void shouldHandleNumericStrings() {
            trie.insert("123");
            trie.insert("456");

            assertTrue(trie.lookup("123"));
            assertTrue(trie.lookup("456"));
        }

        @Test
        @DisplayName("Should handle very long words")
        void shouldHandleVeryLongWords() {
            String longWord = "a".repeat(1000);
            trie.insert(longWord);

            assertTrue(trie.lookup(longWord));
        }
    }
}