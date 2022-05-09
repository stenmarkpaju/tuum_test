package tuum.tuum_test.persistence.model;

public enum Currency {
    EUR(1.0),
    USD(1.06),
    SEK(0.094),
    GBP(1.17);

    public final Double value;

    Currency(Double value) {
        this.value = value;
    }
}
