# Trie
Trie is a data structure wide;ly used in statistics and some other occasions.

字典树（Trie）笔记

# 特别声明

#### 本文只是一篇笔记类的文章，所以不存在什么抄袭之类的。
#### 以下为我研究时参考过的链接(有很多，这里我只列出我记得的)：
#### [Trie(字典树)的应用——查找联系人](http://www.jianshu.com/p/df8fd4de55d5)
#### [trie树](http://www.jianshu.com/p/8524281c220a)
#### [Trie树：应用于统计和排序](http://blog.csdn.net/hguisu/article/details/8131559)
#### [牛人源码，研究代码来源](https://github.com/brianfromoregon/trie)

# 1、字典树的概念
```
字典树，因为它的搜索快捷的特性被单词搜索系统使用，故又称单词查找树。
它是一种树形结构的数据结构。之所以快速，是因为它用空间代替了速度。
```
# 2、字典树的特点：
#### 字典树有三个基本性质：
```
1、根节点不包含字符，除根节点外每一个节点都只包含一个字符
2、从根节点到某一个节点，路径上经过的字符连接起来，就是该节点对应的字符串
3、每个节点的所有子节点包含的字符都不相同。
```
# 3、一个包含以下字符串的字典树结构如下图所示：
```
add
adbc
bye
```
![alt text](https://github.com/Harlan1994/Trie/blob/master/imgs/trie.png "trie.")
# 4、字典树的应用场景
#### 1)、字符串的快速查找
```
给出N个单词组成的熟词表，以及一篇全用小写英文书写的文章，请你按最早出现的顺序写出所有不在熟词表中的生词。
在这道题中，我们可以用数组枚举，用哈希，用字典树，先把熟词建一棵树，然后读入文章进行比较，这种方法效率是比较高的
```
#### 2)、字典树在“串”排序方面的应用
```
给定N个互不相同的仅由一个单词构成的英文名，让你将他们按字典序从小到大输出
用字典树进行排序，采用数组的方式创建字典树，这棵树的每个节点的所有儿子
很显然地按照其字母大小排序,对这棵树进行先序遍历即可
```
#### 3)、字典树在最长公共前缀问题的应用
```
对所有串建立字典树，对于两个串的最长公共前缀的长度即他们所在的节点的公共祖先个数，
于是，问题就转化为最近公共祖先问题。
```
# 5、字典树的数据结构
#### 由以上描述我们可以知道，字典树的数据结构如下：
```
class TrieNode {
	char c;
	int occurances;
	Map<Character, TrieNode> children;
}
```
#### 对以上属性的描述：
##### 1、c, 保存的是该节点的数据，只能是一个字符（注意是一个）
##### 2、occurances, 从单词意思应该知道是发生频率的意思。
##### 3、children, 就是当前节点的子节点，保存的是它的下一个节点的字符。

#### occurances 是指当前节点所对应的字符串在字典树里面出现的次数。

# 7、根据字符串常用的功能，字典树类要实现的特性
```
1）查询是否包含某个字符串
2）查询某个字符串出现的频率
3）插入某个字符串
4）删除某个字符串
5）获取整个字典树的规模，即字典树中包含的不同字符串的个数
#### 基于以上考虑，可以建立一个接口，Trie类只需要实现这个接口即可
```

# 8、基于6所描述的特性创建抽象类如下：
```
public abstract class AbTrie {

	// 判断字典树中是否有该字符串。
	abstract boolean contains(String word);

	// 返回该字符串在字典树中出现的次数。
	abstract int frequency(String word);

	// 插入一个字符串。
	abstract int insert(String word);

	// 删除一个字符串。
	abstract boolean remove(String word);

	// 整个字典树中不同字符串的个数，也就是保存的不同字符串的个数。
	abstract int size();
}
```
#### 各个抽象方法的描述已经很详细的解释了，这里不再赘述

# 9、下面讲解接口中各个方法的实现原理
#### 1）插入字符串
```
1、从头到尾遍历字符串的每一个字符
2、从根节点开始插入，若该字符存在，那就不用插入新节点，要是不存在，则插入新节点
3、然后顺着插入的节点一直按照上述方法插入剩余的节点
4、为了统计每一个字符串出现的次数，应该在最后一个节点插入后occurances++，表示这个字符串出现的次数增加一次
```

#### 2）删除一个字符串
```
1、从root结点的孩子开始（因为每一个字符串的第一个字符肯定在root节点的孩子里），判断该当前节点是否为空，若为空且没有到达所要删除字符串的最后一个字符，则不存在该字符串。若已经到达叶子结点但是并没有遍历完整个字符串，说明整个字符串也不存在，例如要删除的是'harlan1994'，而有'harlan'.
2、只有当要删除的字符串找到时并且最后一个字符正好是叶子节点时才需要删除，而且任何删除动作，只能发生在叶子节点。例如要删除'byebye'，但是字典里还有'byebyeha'，说明byebye不需要删除，只需要更改occurances=0即可标志字典里已经不存在'byebye'这个字符串了。
3、当遍历到最后一个字符时，也就是说字典里存在该字符，必须将当前节点的occurances设为0，这样标志着当前节点代表的这个字符串已经不存在了，而要不要删除，需要考虑2中所提到的情况，也就是说，只有删除只发生在叶子节点上。
```

#### 3）获取字符串出现的次数
```
1、我们在设计数据结构的时候就有了一个occurances属性
2、只需要判断该字符串是否存在，若存在则返回对应字符下的occurances即可
```

#### 4）是否存在某个字符串
```
1、查询字符串是从第一个字符开始的
2、当查询的位置已经超过了字符串的长度，比如要查的是“adc”,但是我们查到树的深度已经超过了c，那么肯定是不存在的
3、如果查询的位置刚好为字符串的长度，这时，就可以判断当前节点的符合要求孩子是否存在，若存在，则字符串存在，否则不存在
4、其余情况则需要继续深入查询，若符合要求的孩子节点存在，则继续查询，否则不存在。
```

#### 5）整棵Trie树的大小，即不同字符串的个数
```
1、返回Trie数据结构中的size属性即可。
2、size属性会在insert，remove两个操作后进行更新
```

# 10、代码实现

#### 1）插入字符串
```
int insert(String s, int pos) {
		
	//如果插入空串，则直接返回
	//此方法调用时从pos=0开始的递归调用，pos指的是插入的第pos个字符
	if (s == null || pos >= s.length())
		return 0;

	// 如果当前节点没有孩子节点，则new一个
	if (children == null)
		children = new HashMap<Character, TrieNode>();

	//创建一个TrieNode
	char c = s.charAt(pos);
	TrieNode n = children.get(c);

	//确保字符保存在即将要插入的节点中
	if (n == null) {
		n = new TrieNode(c);
		children.put(c, n);
	}

	//插入的结束时直到最后一个字符插入，返回的结果是该字符串出现的次数
	//否则继续插入下一个字符
	if (pos == s.length() - 1) {
		n.occurances++;
		return n.occurances;
	} else {
		return n.insert(s, pos + 1);
	}
}
```

#### 2)删除字符串
```
boolean remove(String s, int pos) {
	if (children == null || s == null)
		return false;

	//取出第pos个字符，若不存在，则返回false
	char c = s.charAt(pos);
	TrieNode n = children.get(c);
	if (n == null)
		return false;

	//递归出口是已经到了字符串的最后一个字符，秀嘎occurances=0，代表已经删除了
	//否则继续递归到最后一个字符
	boolean ret;
	if (pos == s.length() - 1) {
		int before = n.occurances;
		n.occurances = 0;
		ret = before > 0;
	} else {
		ret = n.remove(s, pos + 1);
	}

	// 1. If we want to remove 'hello', but there is a 'helloa', you do not
	// need to remove the saved chars, because its occurances has been
	// settled
	// 0 witch means the string s no longer exists.
	// 2.its occurances must be 0, for exmaple,
	// when you want to remove 'harlan1994', but there is no such sequence,
	// there is only 'harlan'
	// so when we reach the last char 'n',the pos != s.length() - 1, its
	// occurances can't be
	// settled into 0, and it > 0, so it is not the sequence that need to be
	// removed.
	// if we just removed a leaf, prune upwards.

	//删除之后，必须删除不必要的字符
	//比如保存的“Harlan”被删除了，那么如果n保存在叶子节点，意味着它虽然被标记着不存在了，但是还占着空间
	//所以必须删除，但是如果“Harlan”删除了，但是Trie里面还保存这“Harlan1994”,那么久不需要删除字符了
	if (n.children == null && n.occurances == 0) {
		children.remove(n.c);
		if (children.size() == 0)
			children = null;
	}

	return ret;
}
```

#### 3）求一个字符串出现的次数
```
TrieNode lookup(String s, int pos) {
	if (s == null)
		return null;

	//如果找的次数已经超过了字符的长度，说明，已经递归到超过字符串的深度了，表明字符串不存在
	if (pos >= s.length() || children == null)
		return null;

	//如果刚好到了字符串最后一个，则只需要返回最后一个字符对应的结点，若节点为空，则表明不存在该字符串
	else if (pos == s.length() - 1)
		return children.get(s.charAt(pos));

	//否则继续递归查询下去，直到没有孩子节点了
	else {
		TrieNode n = children.get(s.charAt(pos));
		return n == null ? null : n.lookup(s, pos + 1);
	}
}

以上kookup方法返回值是一个TrieNode，要找某个字符串出现的次数，只需要看其中的n.occurances即可。
要看是否包含某个字符串，只需要看是否为空节点即可。
```

# 11、下面来一个应用，题目如下：
```
不考虑字母大小写，在一篇文章中只有英文，不包含其余任何字符，求这篇文章中不同单词的个数。
并求所给单词的出现次数。
```

#### 1）建立一个测试类Sample，添加两个方法分别求以上两个问题
#### 2）添加一个求取文件内容，并添加字符串到字典树中的方法，关键代码如下：

```
	...

	private void init() {
		try {
			InputStream in = new FileInputStream(new File(
					"E:\\Eclipse\\trie\\src\\com\\harlan\\trie\\bible.txt"));
			addToDictionary(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToDictionary(InputStream f) throws IOException,
			FileNotFoundException {
		long t = System.currentTimeMillis();
		final int bufSize = 1000;
		int read;
		int numWords = 0;
		InputStreamReader fr = null;
		try {
			fr = new InputStreamReader(f);
			char[] buf = new char[bufSize];
			while ((read = fr.read(buf)) != -1) {
				// TODO modify this split regex to actually be useful
				String[] words = new String(buf, 0, read).split("\\W");
				for (String s : words) {
					mTrie.insert(s);
					numWords++;
				}
			}
		} finally {
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
				}
			}
		}
		System.out.println("Read from file and inserted " + numWords
				+ " words into trie in " + (System.currentTimeMillis() - t)
				/ 1000.0 + " seconds.");
	}

	public int getSize() {
		if (mTrie != null) {
			return mTrie.size();
		}
		return 0;
	}

	public int getCount(String s) {
		return mTrie.frequency(s);
	}

```

#### 测试结果截图如下：
![alt text](https://github.com/Harlan1994/Trie/blob/master/imgs/test.png "How it looks.")

### 代码已经上传至github：https://github.com/Harlan1994/Trie
