/**
 * https://sp18.datastructur.es/materials/proj/proj0/proj0 Plannet.java
 * 2020.12.13
 * 
 */

public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel, yyVel;
    public double mass;
    public String imgFileName;
    private final double gravityConstant = 6.67e-11;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double calcForceExertedBy(Planet p) {
        var dSqaure = calcDistance(p);
        dSqaure *= dSqaure;
        return (gravityConstant * p.mass * this.mass) / (dSqaure);
    }

    private double calcForceExertedByCoordinate(double thisdPos, double argdPos, Planet p) {
        double dc = thisdPos - argdPos;
        dc = dc >= 0 ? dc : -dc;
        var d = calcDistance(p);
        return calcForceExertedBy(p) * dc / d;
    }

    public double calcForceExertedByX(Planet p) {
        return calcForceExertedByCoordinate(this.xxPos, p.xxPos, p);
    }

    public double calcForceExertedByY(Planet p) {
        return calcForceExertedByCoordinate(this.yyPos, p.yyPos, p);
    }

    private void updateVelXandPosX(double a, double dt){
        this.xxVel += a*dt;
        this.xxPos += this.xxVel*dt;
    }
    private void updateVelYandPosY(double a, double dt){
        this.yyVel += a*dt;
        this.yyPos += this.yyVel*dt;
    }
    public void update(double dt, double forceX, double forceY){
        var aX = forceX/mass;
        var aY = forceY/mass;
        updateVelXandPosX(aX,dt);
        updateVelYandPosY(aY,dt);
    }
}
