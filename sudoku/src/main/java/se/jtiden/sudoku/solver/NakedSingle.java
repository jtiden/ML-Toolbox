package main.java.se.jtiden.sudoku.solver;

import main.java.se.jtiden.sudoku.domain.UnsolvedNode;
import main.java.se.jtiden.sudoku.struct.Coordinate;

class NakedSingle extends SolverBuilder {
    @Override
    public Solver build() {
        return () -> {
            for (UnsolvedNode unsolvedNode : board.getUnsolvedNodes()) {
                if (unsolvedNode.numCandidatesLeft() == 1) {
                    int value = unsolvedNode.getCandidates().iterator().next();
                    Coordinate coordinate = unsolvedNode.getCoordinate();
                    System.out.println("[SOLVER] " + coordinate + " Solving as " + value + " because it has only one candidate left.");
                    board.solveNode(coordinate, value);
                    return true;
                }
            }
            return false;
        };
    }
}
