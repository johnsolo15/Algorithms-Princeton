import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    
    private Picture picture;
    
    public SeamCarver(Picture picture) {
        if (picture == null) {
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }
    
    public Picture picture() {
        return new Picture(picture);
    }
    
    public int width() {
        return picture.width();
    }
    
    public int height() {
        return picture.height();
    }
    
    public double energy(int x, int y) { 
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IllegalArgumentException();
        }
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }
        return Math.sqrt(deltaX(x, y) + deltaY(x, y));
    }
    
    private double deltaX(int x, int y) {
        int rgb1 = picture.getRGB(x - 1, y);
        int red1 = (rgb1 & 0xff0000) >> 16;
        int blue1 = (rgb1 & 0xff00) >> 8;
        int green1 = rgb1 & 0xff;
        int rgb2 = picture.getRGB(x + 1, y);
        int red2 = (rgb2 & 0xff0000) >> 16;
        int blue2 = (rgb2 & 0xff00) >> 8;
        int green2 = rgb2 & 0xff;
        return Math.pow(red2 - red1, 2) + Math.pow(green2 - green1, 2) + Math.pow(blue2 - blue1, 2);
    }
    
    private double deltaY(int x, int y) {
        int rgb1 = picture.getRGB(x, y - 1);
        int red1 = (rgb1 & 0xff0000) >> 16;
        int blue1 = (rgb1 & 0xff00) >> 8;
        int green1 = rgb1 & 0xff;
        int rgb2 = picture.getRGB(x, y + 1);
        int red2 = (rgb2 & 0xff0000) >> 16;
        int blue2 = (rgb2 & 0xff00) >> 8;
        int green2 = rgb2 & 0xff;
        return Math.pow(red2 - red1, 2) + Math.pow(green2 - green1, 2) + Math.pow(blue2 - blue1, 2);
    }
    
    public int[] findHorizontalSeam() {
        int[][] edgeTo = new int[width()][height()];
        double[][] distTo = new double[width()][height()];
        double[][] energy = new double[width()][height()];
        
        for (int i = 0; i < distTo.length; i++) {
            for (int j = 0; j < distTo[i].length; j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < height(); i++) {
            distTo[0][i] = 0;
        }
        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[i].length; j++) {
                energy[i][j] = energy(i, j);
            }
        }
        
        for (int y = 0; y < width() - 1; y++) {
            for (int x = 0; x < height(); x++) {
                if (x > 0) {
                    if (distTo[y + 1][x - 1] > distTo[y][x] + energy[y + 1][x - 1]) {
                        distTo[y + 1][x - 1] = distTo[y][x] + energy[y + 1][x - 1];
                        edgeTo[y + 1][x - 1] = x;
                    }
                }
                if (distTo[y + 1][x] > distTo[y][x] + energy[y + 1][x]) {
                    distTo[y + 1][x] = distTo[y][x] + energy[y + 1][x];
                    edgeTo[y + 1][x] = x;
                }
                if (x < height() - 1) {
                    if (distTo[y + 1][x + 1] > distTo[y][x] + energy[y + 1][x + 1]) {
                        distTo[y + 1][x + 1] = distTo[y][x] + energy[y + 1][x + 1];
                        edgeTo[y + 1][x + 1] = x;
                    }
                }
            }
        }
        int min = 0;
        double minDist = distTo[width() - 1][0]; 
        for (int x = 1; x < height(); x++) { 
            if (distTo[width() - 1][x] < minDist) {
                minDist = distTo[width() - 1][x];
                min = x;
            }
        }
        
        int[] seam = new int[width()];
        int parent = min;
        seam[width() - 1] = parent;
        for (int y = width() - 1; y > 0; y--) {
            parent = edgeTo[y][parent];
            seam[y - 1] = parent;
        } 
        
        return seam;
    }
    
    public int[] findVerticalSeam() {
        int[][] edgeTo = new int[height()][width()];
        double[][] distTo = new double[height()][width()];
        double[][] energy = new double[height()][width()];
        
        for (int i = 0; i < distTo.length; i++) {
            for (int j = 0; j < distTo[i].length; j++) {
                distTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        for (int i = 0; i < width(); i++) {
            distTo[0][i] = 0;
        }
        for (int i = 0; i < energy.length; i++) {
            for (int j = 0; j < energy[i].length; j++) {
                energy[i][j] = energy(j, i);
            }
        }
        
        for (int y = 0; y < height() - 1; y++) {
            for (int x = 0; x < width(); x++) {
                if (x > 0) {
                    if (distTo[y + 1][x - 1] > distTo[y][x] + energy[y + 1][x - 1]) {
                        distTo[y + 1][x - 1] = distTo[y][x] + energy[y + 1][x - 1];
                        edgeTo[y + 1][x - 1] = x;
                    }
                }
                if (distTo[y + 1][x] > distTo[y][x] + energy[y + 1][x]) {
                    distTo[y + 1][x] = distTo[y][x] + energy[y + 1][x];
                    edgeTo[y + 1][x] = x;
                }
                if (x < width() - 1) {
                    if (distTo[y + 1][x + 1] > distTo[y][x] + energy[y + 1][x + 1]) {
                        distTo[y + 1][x + 1] = distTo[y][x] + energy[y + 1][x + 1];
                        edgeTo[y + 1][x + 1] = x;
                    }
                }
            }
        }
        int min = 0;
        double minDist = distTo[height() - 1][0]; 
        for (int x = 1; x < width(); x++) { 
            if (distTo[height() - 1][x] < minDist) {
                minDist = distTo[height() - 1][x];
                min = x;
            }
        }
        
        int[] seam = new int[height()];
        int parent = min;
        seam[height() - 1] = parent;
        for (int y = height() - 1; y > 0; y--) {
            parent = edgeTo[y][parent];
            seam[y - 1] = parent;
        } 
        
        return seam;
    }
    
    public void removeHorizontalSeam(int[] seam) {
        if (height() <= 1) {
            throw new IllegalArgumentException();
        }
        checkSeam(seam, false);
        Picture newPic = new Picture(width(), height() - 1);
        for (int x = 0; x < width(); x++) {
            int i = 0;
            for (int y = 0; y < height() - 1; y++) {
                if (seam[x] == y) {
                    i++;
                }
                newPic.setRGB(x, y, picture.getRGB(x, i));
                i++;
            }
        }
        picture = newPic;
    }
    
    public void removeVerticalSeam(int[] seam) {
        if (width() <= 1) {
            throw new IllegalArgumentException();
        }
        checkSeam(seam, true);
        Picture newPic = new Picture(width() - 1, height());
        for (int y = 0; y < height(); y++) {
            int i = 0;
            for (int x = 0; x < width() - 1; x++) {
                if (seam[y] == x) {
                    i++;
                }
                newPic.setRGB(x, y, picture.getRGB(i, y));
                i++;
            }
        }
        picture = newPic;
    }
    
    private void checkSeam(int[] seam, boolean vertical) {
        int height = 0;
        int width = 0;
        if (vertical) {
            height = height();
            width = width();
        } else {
            height = width();
            width = height();
        }
        if (seam == null || seam.length != height) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length; i++) {
            if (seam[i] < 0 || seam[i] >= width) {
                throw new IllegalArgumentException();
            }
            if (i != 0) {
                if (Math.abs(seam[i] - seam[i - 1]) > 1) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
    
}