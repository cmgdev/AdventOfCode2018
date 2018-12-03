package day3;

import java.util.ArrayList;
import java.util.List;

public class Fabric {

    private Cloth cloth;
    private List<LineInfo> lineInfos;

    public Fabric(int x, int y) {
        this.cloth = new Cloth(x, y);
    }

    public void slice(List<String> lines) {
        this.lineInfos = new ArrayList<>();

        for (String line : lines) {
            LineInfo lineInfo = new LineInfo(line);
            lineInfos.add(lineInfo);

            for (int i = lineInfo.getStartX(); i < lineInfo.getEndX(); i++) {
                for (int j = lineInfo.getStartY(); j < lineInfo.getEndY(); j++) {
                    cloth.occupy(i, j);
                }
            }
        }
    }

    public int countOverlaps() {
        if (this.lineInfos == null || this.lineInfos.isEmpty()) return 0;

        return cloth.countOverlaps();
    }

    public List<String> getIntactSlices() {
        List<String> intactSlices = new ArrayList<>();

        for (LineInfo lineInfo : lineInfos) {
            if (cloth.isIntact(lineInfo)) intactSlices.add(lineInfo.getId());
        }

        return intactSlices;
    }

    private class Cloth {
        private int[][] cloth;

        public Cloth(int x, int y) {
            this.cloth = new int[x][y];
        }

        public void occupy(int x, int y) {
            cloth[x][y]++;
        }

        public boolean isOverOccupied(int x, int y) {
            return cloth[x][y] > 1;
        }

        public int countOverlaps() {
            int count = 0;
            for (int i = 0; i < cloth.length; i++) {
                for (int j = 0; j < cloth[i].length; j++) {
                    if (isOverOccupied(i, j)) {
                        count++;
                    }
                }
            }
            return count;
        }

        public boolean isIntact(LineInfo lineInfo) {
            for (int i = lineInfo.getStartX(); i < lineInfo.getEndX(); i++) {
                for (int j = lineInfo.getStartY(); j < lineInfo.getEndY(); j++) {
                    if (isOverOccupied(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private class LineInfo {

        private int startX;

        private int startY;

        private int endX;

        private int endY;

        private String id;

        /*
         * line format:
         * #id @ startX,startY: lengthxheight
         * example: #1 @ 872,519: 18x18
         */
        public LineInfo(String line) {
            String[] tokens = line.split(" ");
            this.id = tokens[0];

            String[] coordinates = (tokens[2].replace(":", "")).split(",");
            this.startX = Integer.parseInt(coordinates[0]);
            this.startY = Integer.parseInt(coordinates[1]);

            String[] size = (tokens[3]).split("x");
            this.endX = this.startX + Integer.parseInt(size[0]);
            this.endY = this.startY + Integer.parseInt(size[1]);
        }

        public int getStartX() {
            return startX;
        }

        public int getStartY() {
            return startY;
        }

        public int getEndX() {
            return endX;
        }

        public int getEndY() {
            return endY;
        }

        public String getId() {
            return id;
        }

    }
}
