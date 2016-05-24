package com.harlan.trie;


public class Trie extends AbTrie {

	private int size;
	private boolean caseSensitive;
	final TrieNode root;

	public Trie(boolean caseSensitive) {
		root = new TrieNode((char) 0);
		size = 0;
		this.caseSensitive = caseSensitive;
	}

	@Override
	public boolean contains(String word) {
		if (word == null)
			return false;

		if (root == null) {
			throw new RuntimeException(
					"root is not initialized. you should call Trie(boolean) to initialize a Trie");
		}

		return root.lookup(caseSensitive ? word : word.toLowerCase(), 0) != null;
	}

	@Override
	public int frequency(String word) {
		if (word == null)
			return 0;
		if (root == null) {
			throw new RuntimeException(
					"root is not initialized. you should call Trie(boolean) to initialize a Trie");
		}
		TrieNode n = root.lookup(caseSensitive ? word : word.toLowerCase(), 0);
		return n == null ? 0 : n.occurances;
	}

	@Override
	public int insert(String word) {
		if (word == null)
			return 0;

		if (root == null) {
			throw new RuntimeException(
					"root is not initialized. you should call Trie(boolean) to initialize a Trie");
		}

		int times = root.insert(caseSensitive ? word : word.toLowerCase(), 0);
		if (times == 1) {
			// 如果插入后出现的次数为1，则是新字符串
			size++;
		}
		return times;
	}

	@Override
	public boolean remove(String word) {
		if (word == null)
			return false;

		if (root == null) {
			throw new RuntimeException(
					"root is not initialized. you should call Trie(boolean) to initialize a Trie");
		}

		if (root.remove(caseSensitive ? word : word.toLowerCase(), 0)) {
			size--;
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		if (root == null) {
			throw new RuntimeException(
					"root is not initialized. you should call Trie(boolean) to initialize a Trie");
		}
		return size;
	}
}
