public class NBody {
    static public double readRadius(String fileName) {
        In file = new In(fileName);
        file.readInt();
        var result = file.readDouble();
        file.close();
        return result;
    }

    static public Planet[] readPlanets(String fileName) {
        In file = new In(fileName);
        int planetCount = file.readInt();
        file.readDouble();// radius
        Planet[] planetArray = new Planet[planetCount];
        for (int i = 0; i < planetCount; i++) {
            var xx = file.readDouble();
            var yy = file.readDouble();
            var vx = file.readDouble();
            var vy = file.readDouble();
            var mass = file.readDouble();
            var imgFileName = file.readString();
            planetArray[i] = new Planet(xx, yy, vx, vy, mass, imgFileName);
        }
        file.close();
        return planetArray;
    }

    private static void drawBackground() {
        StdDraw.picture(0, 0, "images/starfield.jpg");
    }

    private static void drawPictureOfPlanet(Planet p) {
        var x = p.xxPos;
        var y = p.yyPos;
        var imgFileName = p.imgFileName;
        var scaleConstant = 1000000000.;
        var imageFileName = "images/" + imgFileName;
        StdDraw.picture(x / scaleConstant, y / scaleConstant, imageFileName);
    }

    public static void main(String[] args) {
        final var scaleConstant = 1000000000.;
        var T = Double.parseDouble(args[0]);
        var dt = Double.parseDouble(args[1]);
        var filename = args[2];
        var uniRadius = readRadius(filename);
        var planets = readPlanets(filename);
        StdDraw.setScale(-uniRadius / scaleConstant, uniRadius / scaleConstant);
        StdDraw.clear();
        drawBackground();
        for (int i = 0; i < planets.length; i++) {
            drawPictureOfPlanet(planets[i]);
        }
        StdDraw.show();
        StdDraw.pause(2000);
        StdDraw.enableDoubleBuffering();
        int time = 0;
        for (; time < T; time += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            for (int i = 0; i < planets.length; i++) {
                var xForceSum = 0.;
                var yForceSum = 0.;
                for (int j = 0; j < planets.length; j++) {
                    if (i == j)
                        continue;
                    var febx = planets[i].calcForceExertedByX(planets[j]);
                    var feby = planets[i].calcForceExertedByY(planets[j]);
                    var xForceDiNeg = planets[j].xxPos < planets[i].xxPos;
                    var yForceDiNeg = planets[j].yyPos < planets[i].yyPos;
                    xForceSum += xForceDiNeg ? -febx : febx;
                    yForceSum += yForceDiNeg ? -feby : feby;
                }
                xForces[i] = xForceSum;
                yForces[i] = yForceSum;
            }
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            drawBackground();
            for (int i = 0; i < planets.length; i++) {
                drawPictureOfPlanet(planets[i]);
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", uniRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos, planets[i].yyPos,
                    planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
