package se.jtiden.ml.core.impl.circlesadditive;

import se.jtiden.ml.core.api.CircleWithColor;
import se.jtiden.ml.core.api.Evaluator;
import se.jtiden.ml.core.api.Hypothesis;
import se.jtiden.ml.core.api.JTImage;
import se.jtiden.ml.core.impl.MonaLisa;

public class AdditiveHypothesis implements Hypothesis {

    private final JTImage image;
    private final Evaluator<JTImage> imageEvaluator;
    private final MonaLisa monaLisa;
    private Double lossCached;

    public AdditiveHypothesis(
            MonaLisa monaLisa,
            JTImage image,
            Evaluator<JTImage> imageEvaluator) {
        this.monaLisa = monaLisa;
        this.image = image;
        this.imageEvaluator = imageEvaluator;
    }

    @Override
    public double valueFunction() {
        return innerValueFunction();
    }

    public double innerValueFunction() {
        if (lossCached == null) {
            calculateInnerLoss();
        }

        return lossCached;
    }

    private void calculateInnerLoss() {
        lossCached = imageEvaluator.getScore(image);
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


    public AdditiveHypothesis copy() {
        return new AdditiveHypothesis(monaLisa, image.copy(), imageEvaluator);
    }

    public void draw(CircleWithColor circleWithColor) {
        image.getGraphics().draw(circleWithColor);
    }

    public JTImage getImage() {
        return image;
    }
}
