package bsm180003;

import java.util.*;

/** Starter code for P3
 *  @author someone
 */




// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
    // Add fields of MDS here
     HashMap<Integer, TreeMap<Integer, List<Integer>>> table = new HashMap<>();

    // Constructors
    public MDS() {
    }

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated.
       Returns 1 if the item is new, and 0 otherwise.
    */
    /**
     * insert new item whose description is given in a list
     * @param id the id of the item
     * @param price price of the item
     * @param list description
     * retrieve tree map associated with the id from the hashmap
     * @return 1 if it is a new item meaning the id is unique
     * @return 0 if it is an update
     */

    public int insert(int id, int price, List<Integer> list) {
        if (table != null && !table.isEmpty()) {
            if (table.containsKey(id)) {
                TreeMap<Integer, List<Integer>> treemap = table.get(id);
                if (treemap != null && !treemap.isEmpty()) {
                    int p = treemap.firstKey();
                    List<Integer> d = treemap.get(p);
                    if (list == null) {
                        //List<Integer> list1 = treemap.get(p);
                        treemap.remove(p, treemap.get(p));
                        treemap.put(price, d);
                        return 0;
                    }
                    treemap.remove(p, treemap.get(p));
                    treemap.put(price, list);
                    return 0;
                }
                table.replace(id, treemap);

            }

        }
        TreeMap<Integer, List<Integer>> tree_map = new TreeMap<>();
        tree_map.put(price, list);
        table.put(id, tree_map);
        return 1;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    /**
     * find the price given id
     * @param id specific to each item
     * retrieve treemap associated with the id
     * retrieve price (key) associated with the id from the treemap
     * @return price if found
     * @return 0 if not found
     */
    public int find(int id) {
        TreeMap<Integer, List<Integer>> treemap = table.get(id);
        if (treemap != null && !treemap.isEmpty()){
            int des = treemap.firstKey();
            return des;
        }
        return 0;
    }

    /*
       c. Delete(id): delete item from storage.  Returns the sum of the
       ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    /**
     * delete item given id
     * @param id specific to each item
     * retrieve tree map associated with the id
     * use the price to find the description
     * @return total: sum of the ints in the description
     * @return 0 if not found
     */
    public int delete(int id) {
        if (table != null && table.containsKey(id)){
            TreeMap<Integer, List<Integer>> treemap = table.get(id);
            if (treemap != null && !treemap.isEmpty()){
                int p = treemap.firstKey();
                List<Integer> des = treemap.get(p);
                int total = 0;
                for (int i : des){
                    total += i;
                }
                table.remove(id,table.get(id));
                return total;
            }
        }

        return 0;
    }

    /*
       d. FindMinPrice(n): given an integer, find items whose description
       contains that number (exact match with one of the ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    /**
     * given n, find lowest of price of items with the # in their description
     * @param n
     * iterate through hashmap and retrieve treemap
     * use the price to find the description
     * if description contains n add its price to a temp list
     * iterate through temp list, and find the lowest value in it
     * @return lowest price
     * @return 0 if not found
     */
    public int findMinPrice(int n) {
        List<Integer> des = new ArrayList<>();
        for(Map.Entry<Integer, TreeMap<Integer, List<Integer>>> i : table.entrySet()){
            TreeMap<Integer, List<Integer>> r = i.getValue();
            List<Integer> k = r.get(r.firstKey());
            if (k.contains(n)){
                des.add(r.firstKey());
            }
        }
        if(!des.isEmpty()){
            int minp = des.get(0);
            for (int i : des){
                if(i < minp){
                    minp = i;
                }
            }
            return minp;
        }
        return 0;

    }

    /*
       e. FindMaxPrice(n): given an integer, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    /**
     * given n, find highest of price of items with the # in their description
     * @param n
     * iterate through hashmap and retrieve treemap
     * use the price to find the description
     * if description contains n add its price to a temp list
     * iterate through temp list, and find the highest value in it
     * @return highest price
     * @return 0 if not found
     */
    public int findMaxPrice(int n) {

        List<Integer> det = new ArrayList<>();
        for(Map.Entry<Integer, TreeMap<Integer, List<Integer>>> i : table.entrySet()){
            TreeMap<Integer, List<Integer>> r = i.getValue();
            List<Integer> k = r.get(r.firstKey());
            if (k.contains(n)){
                det.add(r.firstKey());
            }
        }
        if(!det.isEmpty()){
            int maxp = det.get(0);
            for (int i : det){
                maxp = Math.max(maxp,i);
            }
            return maxp;
        }
        return 0;



    }
    /**
     * given n, find the number of items within the price range and has n in their description
     * @param n
     * @param high the highest price it can be
     * @param low the lowest price it can be
     * iterate through hashmap and retrieve treemap
     * use the price to find the description
     * if description contains n & it is within the price range, add its price to a temp list
     * iterate through temp list, and count the number of items in there
     * @return total number of items
     * @return 0 if not found
     */

    /*
       f. FindPriceRange(n,low,high): given int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(int n, int low, int high) {
        List<Integer> det = new ArrayList<>();
        for(Map.Entry<Integer, TreeMap<Integer, List<Integer>>> i : table.entrySet()){
            TreeMap<Integer, List<Integer>> r = i.getValue();
            int t = r.firstKey();
            List<Integer> k = r.get(t);
            if (t >= low && t <= high && k.contains(n)){
                det.add(t);
            }
        }
        if(!det.isEmpty()){
            int total = 0;
            for (int i : det){
                total += 1;
            }
            return total;

        }
        return 0;
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    /**
     * given a list, if n in the description of the item, remove n and get the sum of the n's removed
     * @param id
     * @param list A list of integers
     * iterate through hashmap to see if the id exist
     * retrieve the treemap of the id and the description list
     * iterate through the  description list, if element in description exist in list
     * Add element to the hashset then remove it from the description list
     * iterate through hashset, and sum the elements
     * @return the sum
     * @return 0 if not found
     */

    public int removeNames(int id, List<Integer> list) {
        Set<Integer> ted = new HashSet<>(); // temp list for removed elements && handle duplicates
        List<Integer> des = new LinkedList<>();
        if (table != null && table.containsKey(id)) {
            TreeMap<Integer, List<Integer>> treemap = table.get(id);
            if (treemap != null && !treemap.isEmpty()) {
                int p = treemap.firstKey();
                des = treemap.get(p);
                Iterator<Integer> m = des.iterator();
                while(m.hasNext()) {
                    int s = m.next();
                    if (list.contains(s)) {
                        ted.add(s);
                        m.remove();
                    }
                }
            }
        }
        if(!ted.isEmpty()) {
            int total = 0;
            for (int i : ted) {
                total += i;
            }
            return total;
        }

        return 0;
    }
}
