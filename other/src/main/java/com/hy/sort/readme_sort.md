![](./images/sort.png)

![](./images/fuzadu.png)

#### 相关概念
- 稳定：如果a原本在b前面，而a=b，排序之后a仍然在b的前面。
- 不稳定：如果a原本在b的前面，而a=b，排序之后 a 可能会出现在 b 的后面。
- 时间复杂度：对排序数据的总的操作次数。反映当n变化时，操作次数呈现什么规律。
- 空间复杂度：是指算法在计算机内执行时所需存储空间的度量，它也是数据规模n的函数。

### 冒泡排序（Bubble Sort）
![](./images/bubbleSort.gif)
```javascript
function bubbleSort(arr) {
    var len = arr.length;
    for(var i = 0; i < len - 1; i++) {
        for(var j = 0; j < len - 1 - i; j++) {
            if(arr[j] > arr[j+1]) {        // 相邻元素两两对比
                var temp = arr[j+1];        // 元素交换
                arr[j+1] = arr[j];
                arr[j] = temp;
            }
        }
    }
    return arr;
}
```

### 快速排序（Quick Sort）
> 分治法拆分为N段进行排序，一端记录小的部分，一端记录大的部分。<br>
> 通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，以达到整个序列有序。
![](./images/quickSort.gif)
```javascript
function quickSort(arr, left, right) {
    var len = arr.length,
        partitionIndex,
        left = typeof left != 'number'? 0 : left,
        right = typeof right != 'number'? len - 1 : right;
 
    if(left < right) {
        partitionIndex = partition(arr, left, right);
        quickSort(arr, left, partitionIndex-1);
        quickSort(arr, partitionIndex+1, right);
    }
    return arr;
}
 
function partition(arr, left ,right) {     // 分区操作
    var pivot = left,                      // 设定基准值（pivot）
        index = pivot + 1;
    for(var i = index; i <= right; i++) {
        if(arr[i] < arr[pivot]) {
            swap(arr, i, index);
            index++;
        }       
    }
    swap(arr, pivot, index - 1);
    return index-1;
}
 
function swap(arr, i, j) {
    var temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```

### 插入排序（Insertion Sort）
> 对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
![](./images/insertionSort.gif)
```javascript
function insertionSort(arr) {
    var len = arr.length;
    var preIndex, current;
    for(var i = 1; i < len; i++) {
        preIndex = i - 1;
        current = arr[i];
        while(preIndex >= 0 && arr[preIndex] > current) {
            arr[preIndex + 1] = arr[preIndex];
            preIndex--;
        }
        arr[preIndex + 1] = current;
    }
    return arr;
}
```

### 希尔排序（Shell Sort）
> 1959年Shell发明，第一个突破O(n2)的排序算法，是简单插入排序的改进版。它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。。
![](./images/shellSort.gif)
```javascript
function shellSort(arr) {
    var len = arr.length;
    for(var gap = Math.floor(len / 2); gap > 0; gap = Math.floor(gap / 2)) {
        // 注意：这里和动图演示的不一样，动图是分组执行，实际操作是多个分组交替执行
        for(var i = gap; i < len; i++) {
            var j = i;
            var current = arr[i];
            while(j - gap >= 0 && current < arr[j - gap]) {
                 arr[j] = arr[j - gap];
                 j = j - gap;
            }
            arr[j] = current;
        }
    }
    return arr;
}
```

### 选择排序（Selection Sort）
![](./images/selectionSort.gif)
```javascript
function selectionSort(arr) {
    var len = arr.length;
    var minIndex, temp;
    for(var i = 0; i < len - 1; i++) {
        minIndex = i;
        for(var j = i + 1; j < len; j++) {
            if(arr[j] < arr[minIndex]) {     // 寻找最小的数
                minIndex = j;                 // 将最小数的索引保存
            }
        }
        temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }
    return arr;
} 
```

### 归并排序（Merge Sort）
> 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。 
![](./images/mergeSort.gif)
> 算法描述
>- 把长度为n的输入序列分成两个长度为n/2的子序列；
>- 对这两个子序列分别采用归并排序；
>- 将两个排序好的子序列合并成一个最终的排序序列。
```javascript
function mergeSort(arr) {
    var len = arr.length;
    if(len < 2) {
        returnarr;
    }
    var middle = Math.floor(len / 2),
        left = arr.slice(0, middle),
        right = arr.slice(middle);
    returnmerge(mergeSort(left), mergeSort(right));
}
 
function merge(left, right) {
    var result = [];
 
    while(left.length>0 && right.length>0) {
        if(left[0] <= right[0]) {
            result.push(left.shift());
        } else{
            result.push(right.shift());
        }
    }
 
    while(left.length)
        result.push(left.shift());
 
    while(right.length)
        result.push(right.shift());
 
    return result;
}
```

### 堆排序（Heap Sort）
> 归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法（Divide and Conquer）的一个非常典型的应用。将已有序的子序列合并，得到完全有序的序列；即先使每个子序列有序，再使子序列段间有序。若将两个有序表合并成一个有序表，称为2-路归并。 
![](./images/HeapSort.gif)
> 算法描述
>- 将初始待排序关键字序列(R1,R2….Rn)构建成大顶堆，此堆为初始的无序区；
>- 将堆顶元素R[1]与最后一个元素R[n]交换，此时得到新的无序区(R1,R2,……Rn-1)和新的有序区(Rn),且满足R[1,2…n-1]<=R[n]；
>- 由于交换后新的堆顶R[1]可能违反堆的性质，因此需要对当前无序区(R1,R2,……Rn-1)调整为新堆，然后再次将R[1]与无序区最后一个元素交换，得到新的无序区(R1,R2….Rn-2)和新的有序区(Rn-1,Rn)。不断重复此过程直到有序区的元素个数为n-1，则整个排序过程完成。

### 计数排序（Counting Sort）
> 计数排序不是基于比较的排序算法，其核心在于将输入的数据值转化为键存储在额外开辟的数组空间中。 作为一种线性时间复杂度的排序，
> 计数排序要求输入的数据必须是有确定范围的整数。 
![](./images/countingSort.gif)
> 算法描述
>- 找出待排序的数组中最大和最小的元素；
>- 统计数组中每个值为i的元素出现的次数，存入数组C的第i项；
>- 对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）；
>- 反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1
```javascript
function countingSort(arr, maxValue) {
    var bucket = newArray(maxValue + 1),
        sortedIndex = 0;
        arrLen = arr.length,
        bucketLen = maxValue + 1;
 
    for(var i = 0; i < arrLen; i++) {
        if(!bucket[arr[i]]) {
            bucket[arr[i]] = 0;
        }
        bucket[arr[i]]++;
    }
 
    for(var j = 0; j < bucketLen; j++) {
        while(bucket[j] > 0) {
            arr[sortedIndex++] = j;
            bucket[j]--;
        }
    }
 
    return arr;
}
```

### 桶排序（Bucket Sort）
> 桶排序是计数排序的升级版。它利用了函数的映射关系，高效与否的关键就在于这个映射函数的确定。桶排序 (Bucket sort)的工作的原理：假设输入数据服从均匀分布，
> 将数据分到有限数量的桶里，每个桶再分别排序（有可能再使用别的排序算法或是以递归方式继续使用桶排序进行排）。 
![](./images/bucketSort.png)
> 算法描述
>- 设置一个定量的数组当作空桶；
>- 遍历输入数据，并且把数据一个一个放到对应的桶里去；
>- 对每个不是空的桶进行排序；
>- 从不是空的桶里把排好序的数据拼接起来。
```javascript
function bucketSort(arr, bucketSize) {
    if(arr.length === 0) {
      returnarr;
    }
 
    var i;
    var minValue = arr[0];
    var maxValue = arr[0];
    for(i = 1; i < arr.length; i++) {
      if(arr[i] < minValue) {
          minValue = arr[i];                // 输入数据的最小值
      } elseif(arr[i] > maxValue){
          maxValue = arr[i];                // 输入数据的最大值
      }
    }
 
    // 桶的初始化
    var DEFAULT_BUCKET_SIZE = 5;            // 设置桶的默认数量为5
    bucketSize = bucketSize || DEFAULT_BUCKET_SIZE;
    var bucketCount = Math.floor((maxValue - minValue) / bucketSize) + 1;  
    var buckets = newArray(bucketCount);
    for(i = 0; i < buckets.length; i++) {
        buckets[i] = [];
    }
 
    // 利用映射函数将数据分配到各个桶中
    for(i = 0; i < arr.length; i++) {
        buckets[Math.floor((arr[i] - minValue) / bucketSize)].push(arr[i]);
    }
 
    arr.length = 0;
    for(i = 0; i < buckets.length; i++) {
        insertionSort(buckets[i]);                      // 对每个桶进行排序，这里使用了插入排序
        for(varj = 0; j < buckets[i].length; j++) {
            arr.push(buckets[i][j]);                     
        }
    }
 
    return arr;
}
```


### 基数排序（Radix Sort）
> 基数排序是按照低位先排序，然后收集；再按照高位排序，然后再收集；依次类推，直到最高位。有时候有些属性是有优先级顺序的，先按低优先级排序，再按高优先级排序。
> 最后的次序就是高优先级高的在前，高优先级相同的低优先级高的在前。 
![](./images/radixSort.gif)
> 算法描述
>- 取得数组中的最大数，并取得位数；
>- arr为原始数组，从最低位开始取每个位组成radix数组；
>- 对radix进行计数排序（利用计数排序适用于小范围数的特点）；

```javascript
varcounter = [];
function radixSort(arr, maxDigit) {
    var mod = 10;
    var dev = 1;
    for(var i = 0; i < maxDigit; i++, dev *= 10, mod *= 10) {
        for(var j = 0; j < arr.length; j++) {
            var bucket = parseInt((arr[j] % mod) / dev);
            if(counter[bucket]==null) {
                counter[bucket] = [];
            }
            counter[bucket].push(arr[j]);
        }
        var pos = 0;
        for(var j = 0; j < counter.length; j++) {
            var value = null;
            if(counter[j]!=null) {
                while((value = counter[j].shift()) != null) {
                      arr[pos++] = value;
                }
          }
        }
    }
    return arr;
}
```



