import java.util.ArrayList;
import java.util.LinkedList;

public class HashMapCode {
    static class HashMap<K,V> //generics // parameterized type
    {
        private class Node
        {
            K key;
            V value;

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private int n; //total Nodes
        private int N; // total buckets
        private LinkedList<Node> buckets[];

        public HashMap() {
            this.N =4;
            this.buckets = new LinkedList[4];
            for(int i=0;i<4;i++){
                this.buckets[i] = new LinkedList<>();
            }
        }

        public void put(K key,V value) {
            int bi = hashFunction(key);
            int di = searchInLL(key,bi); //data  index

            if(di==-1){
                //key doesn't exist
                buckets[bi].add(new Node(key,value));
                n++;
            }else {
                //key exits
                Node data =buckets[bi].get(di);
                data.value = value;
            }

            double lambda = (double) n/N;

            if(lambda>2.0){
                //2.0 is threshold value for creating new clone hashmap
                //rehashing
                rehash();
            }
        }

        private int hashFunction(K key) {
            //bi should be between 0 to N
            int bi = key.hashCode();
            //234567
            //-45645
            //bi can be positive or negative
            return Math.abs(bi) % N;
        }

        private int searchInLL(K key, int bi){
            LinkedList<Node> ll = buckets[bi];
            int di = 0;
            for(int i=0;i<ll.size();i++){
                if(ll.get(i).key==key){
                    return i;
                }
            }
            return -1;
        }

        private void rehash() {
            LinkedList<Node> oldBucket[] = buckets;
            buckets = new LinkedList[N*2];

            for(int i = 0 ;i<N*2;i++){
                buckets[i] =new LinkedList<>();
            }

            for(int i=0;i<oldBucket.length; i++){
                LinkedList<Node> ll = oldBucket[i];
                for(int j=0;j<ll.size();j++) {
                    Node node = ll.get(j);
                    put(node.key,node.value);
                }
            }
        }

        public V get(K key){
            int bi = hashFunction(key);
            int di = searchInLL(key,bi);

            if(di==-1){
                return null;
            }else {
                Node node = buckets[bi].get(di);
                return node.value;
            }
        }

        public boolean containsKey(K key){
            int bi = hashFunction(key);
            int di = searchInLL(key,bi);
            return di != -1;
        }

        public V remove(K key){
            int bi = hashFunction(key);
            int di = searchInLL(key,bi);
            if(di==-1){
                return null;
            }else{
                Node node = buckets[bi].remove(di);
                return node.value;
            }
        }

        public boolean isEmpty(){
            return n==0;
        }

        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<>();
            for(int i=0;i<buckets.length;i++){
                LinkedList<Node> ll = buckets[i];
                for(int j=0;j<ll.size();j++){
                    Node node = ll.get(j);
                    keys.add(node.key);
                }
            }
            return keys;
        }
    }

    public static void main(String[] args){
        HashMap<String,Integer> map =  new HashMap<>();
        map.put("India",90);
        map.put("China",200);
        map.put("US",50);
    }
}
