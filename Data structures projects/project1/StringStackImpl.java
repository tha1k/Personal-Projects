import java.io.PrintStream;
import java.util.NoSuchElementException;


class StringStackImpl implements StringStack{
    Node head = null;
    int size;

    StringStackImpl (){
        size = 0;
    }

    public boolean isEmpty(){
        return (head == null);
    }

    public void push(String item){
        Node node = new Node(item);
        if(isEmpty()){
            this.head = new Node(item);
        }
        else{
            node.setNext(head);
            this.head = node;
        }
        this.size++;

    }

    public String pop() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        String data = head.getData();
        if(head.getNext() == null){
            head = null;
        }
        else{
            head = head.getNext();
        }
        this.size--;
        return data;
    }

    public String peek() throws NoSuchElementException{
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else{
            String data = head.getData();
            return data;
        }
    }

    public int size(){
        return this.size;
    }

    public void printStack(PrintStream stream){
        Node temp = this.head;
        StringBuilder ret = new StringBuilder();

        if(isEmpty()){
            ret.append("Stack is empty");
        }else{ ret.append("head -> ");
            while( temp !=null){//while temp point at a value
                ret.append(temp.getData());
                if(temp.getNext() != null){//while the next item exists
                    ret.append("->");

                }temp=temp.next;
            }
        }

        stream.print(ret.toString());
    stream.flush();
        }
        
    }




