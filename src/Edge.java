public class Edge {
    private int u;
    private int v;
    Edge(){

    }
    Edge(int u , int v){
        this.u = u ;
        this.v = v;
    }
    public void setU(int u){
        this.u = u;
    }
    public void setV(int v){
        this.v = v;
    }
    public int getU(){
        return u;
    }
    public int getV(){
        return v;
    }
}
