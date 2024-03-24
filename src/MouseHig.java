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

    private void validCoord(){
        if ((this.coord[0][0] > this.coord[1][0] && this.coord[0][1] != this.coord[1][1]) || (this.coord[0][0] == this.coord[1][0] && this.coord[0][1] > this.coord[1][1])){
            int [] dCoord = this.coord[0];
            this.coord[0] = this.coord[1];
            this.coord[1] = dCoord; 
            System.out.println("control");
        }
    }

    public int [][] getFullCoords(int len){

        validCoord();

        int nFullRows = this.coord[1][0] - this.coord[0][0];
        int nExtraCells = len - this.coord[0][1] + this.coord[1][1];
        int nAllCells = nFullRows * len + nExtraCells;

        int [][] result = new int [nAllCells][2];

        int nRes = 0;

        for (int i = this.coord[0][0] * (len + 1) + this.coord[0][1]; i <= this.coord[1][0] * (len + 1) + this.coord[1][1]; i++){
            if (i % (len + 1) != 0){   
                int [] qT = new int [2];
                qT[0] = i / (len + 1);
                qT[1] = i % (len + 1);
                result[nRes] = qT;
                nRes += 1;
                // System.out.println(qT[0] + "x" + qT[1]);  // for control of selection 
            }
        }
        
        return result;
    }
}
