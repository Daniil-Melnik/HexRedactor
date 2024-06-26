/*
* Класс-утилита
* Назначение: автомат выделения ячеек в таблице
* */

public class MouseHig {
    private byte cond;
    private int [][] coord;

    public MouseHig(){
        coord = new int [2][2];
        cond = 0;
    }

    public void setCond(byte newCond){
        this.cond = newCond;
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
        //System.out.println(this.coord[0][0] + " " + this.coord[0][1]);
        //System.out.println(this.coord[1][0] + " " + this.coord[1][1]);
        return this.coord;
    }

    private void validCoord(){
        if ((this.coord[0][0] > this.coord[1][0]) || (this.coord[0][0] == this.coord[1][0] && this.coord[0][1] > this.coord[1][1])){
            int [] dCoord = this.coord[0];
            this.coord[0] = this.coord[1];
            this.coord[1] = dCoord; 
            System.out.println("control");
        }
        System.out.println("bad");
    }

    public int [][] getFullCoords(int len){

        validCoord();

        /*
         * Верхнюю и нижнюю строку учитываем по отдельности
         * Остальные принимаем за целые
         */
        int nFullRows = this.coord[1][0] - this.coord[0][0] - 1;
        // int nFullRows = Math.abs(this.coord[1][0] - this.coord[0][0] - 1);
        int nExtraUp = len - this.coord[0][1] + 1;
        int nExtraBottom = this.coord[1][1];
        int nExtraCells = nExtraUp + nExtraBottom;

        int nAllCells = nFullRows * len + nExtraCells;

        System.out.println("coord0 = " + this.coord[0][0] + " " + this.coord[0][1]);
        System.out.println("coord1 = " + this.coord[1][0] + " " + this.coord[1][1]);
        System.out.println("nFullRows = " + nFullRows);

        System.out.println("nFullRows = " + nFullRows);
        System.out.println("nExtraCells = " + nExtraCells);
        System.out.println("nAllCells = " + nAllCells);

        System.out.println("nExtraCellsUp = " + (len - this.coord[0][1] + 1));
        System.out.println("nExtraCellsBottom = " + (this.coord[1][1]));

        int [][] res = new int [nAllCells][2];

        int nRes = 0;

        for (int i = this.coord[0][0] * (len + 1) + this.coord[0][1]; i <= this.coord[1][0] * (len + 1) + this.coord[1][1]; i++){
            if (i % (len + 1) != 0){   
                int [] qT = new int [2];
                qT[0] = i / (len + 1);
                qT[1] = i % (len + 1);
                res[nRes] = qT;
                nRes += 1;
                // System.out.println(qT[0] + "x" + qT[1]);  // for control of selection 
            }
        }
        // for (int i = 0; i < res.length; i++){
        //     System.out.println(res[i][0] + " " + res[i][1]);
        // }
        return res;
    }
}
