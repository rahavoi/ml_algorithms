package com.rahavoi.ml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KMeans
{
    public Point[][] clusterify(Point[] points, int size){
        Point[] centroids = getRandomCendroids(points, size);
        Arrays.sort(centroids);

        Map<Point, List<Point>> clusters;
        Point[] newCentroids;

        do{
            clusters = allocatePointsToCentroids(points, centroids);
            newCentroids = clusters.values().stream().map(this::getMean).toArray(Point[]::new);
            Arrays.sort(newCentroids);

        } while (!Arrays.equals(centroids, newCentroids));

        Collection<List<Point>> finalClusters = clusters.values();
        return finalClusters.stream().filter(c -> c.size() > 0).map(cluster -> cluster.toArray()).toArray(Point[][]::new);
    }

    private Point getMean(List<Point> cluster){

        double totalX = 0;
        double totalY = 0;

        for(Point p : cluster){
            totalX += p.getX();
            totalY += p.getY();
        }

        return new Point(totalX / cluster.size(), totalY / cluster.size());
    }

    private Point findClosestCentroid(Point p, Point[] centroids){
        return Arrays.stream(centroids).min((c1, c2) -> {
            double diff = getDistance(p, c1) - getDistance(p, c2);

            if(diff == 0){
                return 0;
            } else {
                return diff > 0 ? 1 : -1;
            }
        }).get();
    }

    private Map<Point, List<Point>> allocatePointsToCentroids(Point[] points, Point[] centroids){
        Map<Point, List<Point>> centroidsWithClosestPoints = new HashMap<>();

        for(Point p : points){
            Point centroid = findClosestCentroid(p, centroids);
            List<Point> centroidPoints = centroidsWithClosestPoints.getOrDefault(centroid, new ArrayList<>());
            centroidPoints.add(p);
        }

        return centroidsWithClosestPoints;

    }

    private double getDistance(Point a, Point b){
       return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    private Point[] getRandomCendroids(Point[] points, int size){
        double maxY = Arrays.stream(points).max(Comparator.comparing(Point::getY)).get().getY();
        double minY = Arrays.stream(points).min(Comparator.comparing(Point::getY)).get().getY();
        double maxX = Arrays.stream(points).max(Comparator.comparing(Point::getX)).get().getX();
        double minX = Arrays.stream(points).min(Comparator.comparing(Point::getX)).get().getX();

        Point[] centroids = new Point[size];
        Random random = new Random();

        for(int i = 0; i < size; i++){
            double rx = minX + (maxX - minX) * random.nextDouble();
            double ry = minY + (maxY - minY) * random.nextDouble();
            centroids[i] = new Point(rx, ry);
        }

        return centroids;
    }
}

