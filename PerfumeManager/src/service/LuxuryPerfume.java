package service;

// decorator pattern for luxury perfume S 
public class LuxuryPerfume extends Perfume {
    public LuxuryPerfume(Perfume basePerfume) {
        super(basePerfume.getName(),
        		basePerfume.getBrand(), 
        		basePerfume.getPrice() * 1.5, 
        		basePerfume.getQuantity());
		// add luxury to perfume
	}
    //toString method to show luxury perfume
    @Override
    public String toString() {
        return super.toString()
        		+ " (Luxury)";
    }
}
