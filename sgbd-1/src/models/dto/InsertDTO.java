package models.dto;

public class InsertDTO {
    public boolean duplicated = false;
    int[] depths;

    public InsertDTO(boolean duplicated, int[] depths) {
        this.duplicated = duplicated;
        this.depths = depths;
    }

    public InsertDTO() {
    }

    public boolean isDuplicated() {
        return duplicated;
    }

    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }

    public int[] getDepths() {
        return depths;
    }

    public void setDepths(int[] depths) {
        this.depths = depths;
    }
}
