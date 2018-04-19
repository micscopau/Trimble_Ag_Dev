public class AgPolygon {

    //left back, left front, right front, right back
    private EnuPosition lB, rB, lF, rF;

    public AgPolygon(EnuPosition backLeft, EnuPosition backRight, EnuPosition frontLeft, EnuPosition frontRight){
        this.lB = backLeft;
        this.lF = frontLeft;
        this.rB = backRight;
        this.rF = frontRight;
    }

    public EnuPosition get(int i) {
        switch (i) {
            case 0:
                return lB;
            case 1:
                return rB;
            case 2:
                return lF;
            case 3:
                return rF;
            default:
                return new EnuPosition();
        }
    }

    public EnuPosition getlB() {
        return lB;
    }

    public void setlB(EnuPosition lB) {
        this.lB = lB;
    }

    public EnuPosition getlF() {
        return lF;
    }

    public void setlF(EnuPosition lF) {
        this.lF = lF;
    }

    public EnuPosition getrF() {
        return rF;
    }

    public void setrF(EnuPosition rF) {
        this.rF = rF;
    }

    public EnuPosition getrB() {
        return rB;
    }

    public void setrB(EnuPosition rB) {
        this.rB = rB;
    }

}
