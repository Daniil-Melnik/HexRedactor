public class MouseHig {
    private byte cond;
    private int [][] coord;

    public MouseHig(){
        coord = new int [2][2];
        cond = 0;
    }

    public void addCoord(int [] coord){
        switch (this.cond) {
            case 0:
                this.coord[0] = coord;
                this.cond = 1;
                break;

            case 1:
                this.coord[1] = coord;
                this.cond = 2;
                break;
            
            case 2:
                this.coord = new int [2][2];
                this.cond = 0;
                break;
        
            default:
            System.out.println("TEST");
                break;
        }
    }

    public byte getCond(){
        return this.cond;
    }

    public int [][] getCoord(){
        System.out.println(this.coord[0][0] + " " + this.coord[0][1]);
        System.out.println(this.coord[1][0] + " " + this.coord[1][1]);
        return this.coord;
    }

    // public int [][] getFullCoords(int len){
    //     int 
    // }
}
