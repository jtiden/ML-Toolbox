package se.jtiden.ml.core.impl.nearestneighbor;

import se.jtiden.ml.core.api.Hypothesis;
import se.jtiden.ml.core.api.JTColor;
import se.jtiden.ml.core.api.PointWithColor;
import se.jtiden.ml.core.impl.MonaLisa;

import java.util.ArrayList;
import java.util.List;

public class MonaLisaNearestNeighborHypothesis implements Hypothesis {

    private final List<PointWithColor> points;
    private final MonaLisa monaLisa;
    private Double lossCached;

    public MonaLisaNearestNeighborHypothesis(
            MonaLisa monaLisa,
            List<PointWithColor> points) {
        this.monaLisa = monaLisa;
        this.points = points;
    }

    @Override
    public double valueFunction() {
        return innerValueFunction();

        //if (parent == null) {
        //    return innerValueFunction();
        //}
        //return innerValueFunction() - parent.valueFunction();
    }

    public double innerValueFunction() {
        if (lossCached == null) {
            calculateInnerLoss();
        }

        return lossCached;
    }


    private void calculateInnerLoss() {
        MonaLisaNearestNeighborHypothesisPainter painter = new MonaLisaNearestNeighborHypothesisPainter(this);

        double loss = 0;
        for (int y = 0; y < monaLisa.getHeight(); y += 2) {
            for (int x = 0; x < monaLisa.getWidth(); x += 2) {
                loss -= colorDifferenceSquare(
                        getMonaLisa().getColorAt(x, y),
                        painter.getColorAt(x, y));

            }
        }

        lossCached = loss;
    }

    private double colorDifferenceSquare(JTColor color1, JTColor color2) {
        double diff = JTColor.difference(color1, color2);
        return diff * diff;
    }


    public List<PointWithColor> getPoints() {
        return new ArrayList<PointWithColor>(points);
    }

    @Override
    public int compareTo(Hypothesis o) {
        if (this == o) {
            return 0;
        }

        return valueFunction() > o.valueFunction() ? 1 : -1;
    }

    public MonaLisa getMonaLisa() {
        return monaLisa;
    }
}
