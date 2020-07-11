public class ListNode{
        int value;
        ListNode next;
        
        public ListNode(int v, ListNode n){
            this.value = v;
            this.next = n;
        }
        
        public ListNode(int v){
            this(v,null);
        }
        
        public static ListNode insert(int newValue, ListNode current){
            if(current == null || newValue <= current.value){
                return new ListNode(newValue, current);
            }
            current.next = insert(newValue, current.next);
            return current;
        }
        
        public static void printList(ListNode list){
            ListNode temp = list;
            if(temp!=null){
                if(temp.next==null){
                    System.out.print(temp.value);
                } else {
                    while(temp.next != null){
                        System.out.print(temp.value+", ");
                        temp = temp.next;
                    }
                    System.out.print(temp.value);
                }
            }
        }

}