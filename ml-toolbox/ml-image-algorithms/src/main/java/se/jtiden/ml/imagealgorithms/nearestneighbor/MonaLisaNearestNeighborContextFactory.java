package se.jtiden.ml.imagealgorithms.nearestneighbor;

import se.jtiden.ml.imagealgorithms.ContextImpl;
import se.jtiden.ml.imagealgorithms.MonaLisa;
import se.jtiden.ml.imagealgorithms.MonaLisaAlgorithmPainter;
import se.jtiden.ml.imagealgorithms.algorithm.api.IterativeAlgorithm;
import se.jtiden.ml.imagealgorithms.evaluator.DifferenceSquaredEvaluator;
import se.jtiden.ml.imagealgorithms.painter.HypothesisPainterFactory;

public class MonaLisaNearestNeighborContextFactory {

    public static final int MUTATION_POINT_VARIANCE = 80;
    public static final int NUM_POINTS = 70;
    public static final double CHANCE_TO_MUTATE_POINT = .80;
    public static final int MUTATION_POINT_COLOR_VARIANCE = 80;
    public static final int FAKE_PIXEL_SIZE = 1;

    public ContextImpl getContext() {
        MonaLisa monaLisa = new MonaLisa(FAKE_PIXEL_SIZE);
        DifferenceSquaredEvaluator evaluator = new DifferenceSquaredEvaluator(monaLisa.getImage());
        IterativeAlgorithm algorithm = new MonaLisaNearestNeighborAlgorithm(
                monaLisa,
                NUM_POINTS,
                MUTATION_POINT_VARIANCE,
                CHANCE_TO_MUTATE_POINT,
                MUTATION_POINT_COLOR_VARIANCE,
                evaluator);
        final HypothesisPainterFactory hypothesisPainterFactory = new MonaLisaNearestNeighborHypothesisPainterFactory(
                monaLisa.getWidth(), monaLisa.getHeight());


        return new ContextImpl(
                algorithm,
                new MonaLisaAlgorithmPainter(
                        algorithm,
                        hypothesisPainterFactory)
//                )
                ,
                hypothesisPainterFactory,
                FAKE_PIXEL_SIZE);
    }
}