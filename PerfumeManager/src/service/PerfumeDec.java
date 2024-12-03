package service;

public abstract class PerfumeDec extends Perfume {
    protected Perfume perfume;

    public PerfumeDec(Perfume perfume) {
        super(perfume.getName(), perfume.getBrand(), perfume.getPrice(), perfume.getQuantity());
        this.perfume = perfume;
    }

    @Override
    public String toString() {
        return perfume.toString();
    }
}
