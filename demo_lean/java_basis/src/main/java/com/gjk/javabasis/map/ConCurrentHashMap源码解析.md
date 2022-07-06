# 插入方法--put()

> put方法源码如下：

```java
    public V put(K key, V value) {
        return putVal(key, value, false);
    }
    
    
     final V putVal(K key, V value, boolean onlyIfAbsent) {
         // key和value都不能为null,否则报错
        if (key == null || value == null) throw new NullPointerException();
         //计算key的hash
        int hash = spread(key.hashCode());
         //记录链表中元素个数
        int binCount = 0;
         //这是一个死循环，
        for (Node<K,V>[] tab = table;;) {
            Node<K,V> f; int n, i, fh;
            //如果数组是空或者数组长度为0，则初始化数组
            if (tab == null || (n = tab.length) == 0)
                tab = initTable();
            //如果插入桶中没有元素，则直接插入
            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
                if (casTabAt(tab, i, null,
                             new Node<K,V>(hash, key, value, null)))
                    //此处会通过CAS进行插入，如果插入成功，break跳出循环；
                    //如果插入失败，已经有其他元素了，则再次进入循环重新插入
                    break;                   // no lock when adding to empty bin
            }
            //如果要插入的元素所在的桶的第一个元素的hash是MOVED，则当前线程帮忙一起迁移元素
            else if ((fh = f.hash) == MOVED)
                tab = helpTransfer(tab, f);
            else {
                V oldVal = null;
                //如果桶不为空且不在迁移数据，则锁住这个桶
                synchronized (f) {
                    //再次检查桶中第一个元素是否有变化。防止获取锁之前被其他线程修改
                    //如果有变化进入，重新进入循环
                    if (tabAt(tab, i) == f) {
                        //如果第一个元素的hash大于0，代表当前不在迁移数据，且数据时链表存储的，不是使用的树
                        if (fh >= 0) {
                            binCount = 1;
                            //遍历链表
                            for (Node<K,V> e = f;; ++binCount) {
                                K ek;
                                //如果存在key相同，则替换旧值，跳出循环
                                if (e.hash == hash &&
                                    ((ek = e.key) == key ||
                                     (ek != null && key.equals(ek)))) {
                                    oldVal = e.val;
                                    if (!onlyIfAbsent)
                                        e.val = value;
                                    break;
                                }
                                Node<K,V> pred = e;
                                //如果没有找到key相同的，则新建节点存到链表尾部
                                if ((e = e.next) == null) {
                                    pred.next = new Node<K,V>(hash, key,
                                                              value, null);
                                    break;
                                }
                            }
                        }
                        //如果桶中第一个元素是树，则通过putTreeVal向树中插入值
                        else if (f instanceof TreeBin) {
                            Node<K,V> p;
                            binCount = 2;
                            //如果成功插入树，则putTreeVal返回null.
                            //否则在树中查找节点，替换旧值
                            if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                           value)) != null) {
                                oldVal = p.val;
                                if (!onlyIfAbsent)
                                    p.val = value;
                            }
                        }
                    }
                }
                //如果链表长度大于等于8，则将链表树化
                if (binCount != 0) {
                    if (binCount >= TREEIFY_THRESHOLD)
                        treeifyBin(tab, i);
                    //如果存在旧值，返回旧值
                    if (oldVal != null)
                        return oldVal;
                    //跳出最外层for循环
                    break;
                }
            }
        }
         //成功插入，链表计数+1，扩容也在这个方法里
        addCount(1L, binCount);
        return null;
    }
```

> ConcurrentHashMap主要通过CAS + synchronized + volatile实现线程安全的。put方法主要步骤如下：
>
> * （1）如果桶数组未初始化，则初始化；
> * （2）如果待插入的元素所在的桶为空，则尝试把此元素直接插入到桶的第一个位置；
> * （3）如果正在扩容，则当前线程一起加入到扩容的过程中；
> * （4）如果待插入的元素所在的桶不为空且不在迁移元素，则锁住这个桶（分段锁）；
> * （5）如果当前桶中元素以链表方式存储，则在链表中寻找该元素或者插入元素；
> * （6）如果当前桶中元素以红黑树方式存储，则在红黑树中寻找该元素或者插入元素；
> * （7）如果元素存在，则返回旧值；
> * （8）如果元素不存在，整个Map的元素个数加1，并检查是否需要扩容；