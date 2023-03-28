package application.model;

public class MaltBatch {
    private String kornSort;
    private String batchNr;
    private String tørv;

    public MaltBatch(String kornSort, String batchNr, String tørv) {
        this.kornSort = kornSort;
        this.batchNr = batchNr;
        this.tørv = tørv;
    }
}
