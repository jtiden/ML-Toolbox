package main.java.se.jtiden.sudoku;

public class SudokuSolverFactory {

    public MultiSolver newSudokuSolver(Board board) {
        MultiSolverBuilder builder = new MultiSolverBuilder().withBoard(board);

        verifyNoNodesHaveZeroCandidates(builder);
        removeCandidatesOfSolvedNeighbors(builder);
        singleCandidateSolver(builder);
        solveIfCandidateIsAloneInGroup(builder);
        lockedCandidates(builder);

        return builder.build();
    }

    private void verifyNoNodesHaveZeroCandidates(MultiSolverBuilder builder) {
        builder.withSolver(new SolverBuilder() {
            @Override
            public Solver build() {
                return new Solver() {
                    @Override
                    public boolean trySolve() {
                        for (UnsolvedNode node : board.getUnsolvedNodes()) {
                            if (node.numCandidatesLeft() == 0) {
                                throw new IllegalStateException(node.getCoordinate() + " has no candidates left. Either the sudoku has no solution, or there is an error in the solver");
                            }
                        }
                        return false;
                    }
                };
            }
        });
    }


    private void lockedCandidates(MultiSolverBuilder builder) {
        builder.withSolver(new SolverBuilder() {
            @Override
            public Solver build() {
                return new Solver() {
                    @Override
                    public boolean trySolve() {
                        boolean success = lockedCandidatesInRows();
                        if (success) {
                            return true;
                        }

                        return lockedCandidatesInColumns();
                    }

                    private boolean lockedCandidatesInRows() {
                        return false; // TODO continue
                        //for (int y = 0; y < board.getHeight(); ++y) {

                        //}
                    }

                    private boolean lockedCandidatesInColumns() {
                        return false;  //To change body of created methods use File | Settings | File Templates.
                    }


                };
            }
        });
    }

    private void solveIfCandidateIsAloneInGroup(MultiSolverBuilder builder) {
        builder.withSolver(new SolverBuilder() {
            @Override
            public Solver build() {
                return new Solver() {
                    @Override
                    public boolean trySolve() {
                        boolean success = solveIfCandidateIsAloneInRow();
                        if (success) {
                            return true;
                        }

                        success = solveIfCandidateIsAloneInColumn();
                        if (success) {
                            return true;
                        }

                        success = solveIfCandidateIsAloneInBox();
                        return success;
                    }

                    private boolean solveIfCandidateIsAloneInBox() {
                        for (UnsolvedNode unsolvedNode : board.getUnsolvedNodes()) {
                            for (Integer value : unsolvedNode.getCandidates()) {
                                if (board.countCandidatesInBoxFor(unsolvedNode.getCoordinate(), value) == 1) {
                                    System.out.println("[SOLVER] " + unsolvedNode.getCoordinate() + " Solving as " + value + " because it is the only candidate in the box.");
                                    board.solveNode(unsolvedNode.getCoordinate(), value);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }

                    private boolean solveIfCandidateIsAloneInColumn() {
                        for (UnsolvedNode unsolvedNode : board.getUnsolvedNodes()) {
                            for (Integer value : unsolvedNode.getCandidates()) {
                                if (board.countCandidatesInColumnFor(unsolvedNode.getCoordinate(), value) == 1) {
                                    System.out.println("[SOLVER] " + unsolvedNode.getCoordinate() + " Solving as " + value + " because it is the only candidate in the column.");
                                    board.solveNode(unsolvedNode.getCoordinate(), value);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }

                    private boolean solveIfCandidateIsAloneInRow() {
                        for (UnsolvedNode unsolvedNode : board.getUnsolvedNodes()) {
                            for (Integer value : unsolvedNode.getCandidates()) {
                                if (board.countCandidatesInRowFor(unsolvedNode.getCoordinate(), value) == 1) {
                                    System.out.println("[SOLVER] " + unsolvedNode.getCoordinate() + " Solving as " + value+ " because it is the only candidate in the row.");
                                    board.solveNode(unsolvedNode.getCoordinate(), value);
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                };
            }


        });
    }

    private void removeCandidatesOfSolvedNeighbors(MultiSolverBuilder builder) {
        builder.withSolver(new SolverBuilder() {
            @Override
            public Solver build() {
                return new Solver() {
                    @Override
                    public boolean trySolve() {
                        for (UnsolvedNode unsolvedNode : board.getUnsolvedNodes()) {
                            for (Node neighbor : board.getNeighborsFor(unsolvedNode)) {
                                if (neighbor.isSolved()) {
                                    int neighborValue = neighbor.getValue();
                                    if (unsolvedNode.getCandidates().contains(neighborValue)) {
                                        //System.out.println("[SOLVER] " + unsolvedNode.getCoordinate() + " Removing candidate " + neighborValue  + " because " + neighbor.getCoordinate() + " is " + neighborValue + ".");
                                        unsolvedNode.removeCandidate(neighborValue);
                                        return true;
                                    }
                                }
                            }
                        }

                        return false;
                    }
                };
            }
        });
    }

    private void singleCandidateSolver(MultiSolverBuilder builder) {
        builder.withSolver(new SolverBuilder() {
            @Override
            public Solver build() {
                return new Solver() {
                    @Override
                    public boolean trySolve() {
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
                    }

                };
            }
        });
    }
}