package day03;

import java.util.*;
import java.util.stream.Collectors;

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
                    cloth.occupy(i, j, lineInfo.getId());
                }
            }
        }
    }

    public int countOverlaps() {
        if (this.lineInfos == null || this.lineInfos.isEmpty()) return 0;

        return cloth.countOverlaps();
    }

    public List<String> getIntactSlices() {
        List<String> intact = lineInfos.stream()
                .map(LineInfo::getId)
                .collect(Collectors.toList());
        intact.removeAll(cloth.getOverOccupiedIds());
        return intact;
    }

    private class Cloth {
        Map<String, List<String>> cloth;

        public Cloth(int x, int y) {
            this.cloth = new HashMap<>();
        }

        private String getKey(int x, int y) {
            return x + "." + y;
        }

        public void occupy(int x, int y, String id) {
            List<String> l = cloth.getOrDefault(getKey(x, y), new ArrayList<>());
            l.add(id);
            cloth.put(getKey(x, y), l);
        }

        public boolean isOverOccupied(int x, int y) {
            return cloth.getOrDefault(getKey(x, y), new ArrayList<>()).size() > 1;
        }

        public int countOverlaps() {
            return Math.toIntExact(
                    cloth.values().stream()
                            .filter(i -> i.size() > 1)
                            .count()
            );
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

        public Collection<String> getOverOccupiedIds() {
            return cloth.values().stream()
                    .filter(l -> l.size() > 1)
                    .flatMap(l -> l.stream())
                    .collect(Collectors.toSet());
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
