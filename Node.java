public class Node{
    public String key;
    public String value;

    public Node(String key, String value){
        this.key = key;
        this.value = value;
    }

    /**
     * returns the key in node
     * @return key in node
     */
    public String getKey(){
        return this.key;
    }

    /**
     * returns the value in node
     * @return value in node
     */
    public String getValue(){
        return this.value;
    }

}