package chml;

import static chml.MainWindow.Charges;
import static chml.MainWindow.redrawAll;
import static chml.MainWindow.tester;
import static chml.MainWindow.FT;
import static chml.MainWindow.DM;
import static chml.MainWindow.CMV;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Ilya
 */
public class Scr extends JPanel {

    BufferedImage Buf;

    public int max255(int v) {
        if (v > 255) {
            return 255;
        } else if (v < 0) {
            return 0;
        } else {
            return v;
        }
    }

    public double sq(double a) {
        return a * a;
    }

    public double sg(double a) {
        if (a > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Buf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) Buf.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Color pc = new Color(255, 255, 255);
        Color clrBlack = new Color(0, 0, 0);

        if (redrawAll) {
            double maxf = 0;
            double minf = 0;
            double maxq = Charges.arr[0].v;
            double minq = Charges.arr[0].v;
            double kf = 0;
            for (int k = 0; k < Charges.count; k++) {
                if (minq > Charges.arr[k].v) {
                    minq = Charges.arr[k].v;
                }
                if (maxq < Charges.arr[k].v) {
                    maxq = Charges.arr[k].v;
                }
            }
            double[][] field = new double[this.getWidth()][];
            double[][] vfieldx = new double[this.getWidth()][];
            double[][] vfieldy = new double[this.getWidth()][];
            for (int i = 0; i < field.length; i++) {
                field[i] = new double[this.getHeight()];
                vfieldx[i] = new double[this.getHeight()];
                vfieldy[i] = new double[this.getHeight()];
            }
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    field[i][j] = 0;
                    vfieldx[i][j] = 0;
                    vfieldy[i][j] = 0;
                    //double e = 0;
                    if (FT == 1) {
                        for (int k = 0; k < Charges.count; k++) {
                            int x = i - Charges.arr[k].x;
                            int y = j - Charges.arr[k].y;
                            vfieldx[i][j] += (x * Charges.arr[k].v) / Math.pow(x * x + y * y, 1.5);
                            vfieldy[i][j] += (y * Charges.arr[k].v) / Math.pow(x * x + y * y, 1.5);
                            field[i][j] += 50 * Charges.arr[k].v / (x * x + y * y);
                        }
                    } else {
                        for (int k = 0; k < Charges.count; k++) {
                            field[i][j] += Charges.arr[k].v / (Math.sqrt(Math.pow(i - Charges.arr[k].x, 2) + Math.pow(j - Charges.arr[k].y, 2)));
                        }
                    }
                    if (field[i][j] > maxf) {
                        maxf = field[i][j];
                    }
                    if (field[i][j] < minf) {
                        minf = field[i][j];
                    }
                }
            }
            for (int i = 0; i < this.getWidth(); i++) {
                for (int j = 0; j < this.getHeight(); j++) {
                    switch (DM) {
                        case 1:
                            pc = new Color(230, 255, 230);
                            if (field[i][j] > 0) {
                                kf = 5 * CMV / maxq;
                                pc = new Color(255, max255((int) (255 / (kf * field[i][j]))), max255((int) (255 / (kf * field[i][j]))));
                            } else if (field[i][j] < 0) {
                                kf = -5 * CMV / minq;
                                pc = new Color(max255((int) (-255 / (kf * field[i][j]))), max255((int) (-255 / (kf * field[i][j]))), 255);
                            }
                            break;
                        case 2:
                            maxf = maxq / CMV;
                            minf = minq / CMV;
                            if (field[i][j] > 0) {
                                pc = new Color(255, max255((int) (255 - 255 * (field[i][j] / maxf))), max255((int) (255 - 255 * (field[i][j] / maxf))));
                            } else if (field[i][j] < 0) {
                                pc = new Color(max255((int) (255 - 255 * (field[i][j] / minf))), max255((int) (255 - 255 * (field[i][j] / minf))), 255);
                            }
                            break;
                        case 3:
                            maxf = maxq / CMV;
                            minf = minq / CMV;
                            if (field[i][j] > 0) {
                                //if ((field[i][j] / maxf < 0.72)&&(field[i][j] / maxf > 0.002)) {
                                kf = 5 * CMV / maxq;
                                if ((max255((int) (255 - 255 * (field[i][j] / maxf))) > max255((int) (255 / (kf * field[i][j])))) && (field[i][j] / maxf > 0)) {
                                    pc = new Color(255, max255((int) (255 - 255 * (field[i][j] / maxf))), max255((int) (255 - 255 * (field[i][j] / maxf))));
                                } else {
                                    pc = new Color(255, max255((int) (255 / (kf * field[i][j]))), max255((int) (255 / (kf * field[i][j]))));
                                }
                            } else if (field[i][j] < 0) {
                                kf = -5 * CMV / minq;
                                if ((max255((int) (255 - 255 * (field[i][j] / minf))) > max255((int) (255 / (kf * field[i][j])))) && (field[i][j] / minf > 0)) {
                                    pc = new Color(max255((int) (255 - 255 * (field[i][j] / minf))), max255((int) (255 - 255 * (field[i][j] / minf))), 255);
                                } else {
                                    pc = new Color(max255((int) (-255 / (kf * field[i][j]))), max255((int) (-255 / (kf * field[i][j]))), 255);
                                }
                                pc = new Color(max255((int) (255 - 255 * (field[i][j] / minf))), max255((int) (255 - 255 * (field[i][j] / minf))), 255);
                            }
                            break;
                        default:
                            if (field[i][j] > 0) {
                                kf = 15 * CMV / maxq;
                                if (kf * field[i][j] > 1) {
                                    pc = new Color(255, max255((int) (255 / Math.log(kf * field[i][j]))), max255((int) (255 / Math.log(kf * field[i][j]))));
                                } else {
                                    pc = new Color(255, max255(64 + (int) (- 128 / Math.log(kf * field[i][j]))), max255(196 + (int) (- 64 / Math.log(kf * field[i][j]))));
                                }
                            } else if (field[i][j] < 0) {
                                kf = 15 * CMV / minq;
                                if (kf * field[i][j] > 1) {
                                    pc = new Color(max255((int) (255 / Math.log(kf * field[i][j]))), max255((int) (255 / Math.log(kf * field[i][j]))), 255);
                                } else {
                                    pc = new Color(max255(64 + (int) (- 128 / Math.log(kf * field[i][j]))), max255(196 + (int) (- 64 / Math.log(kf * field[i][j]))), 255);
                                }
                            }
                            break;
                    }
                    Buf.setRGB(i, j, pc.getRGB());
                }
            }
            // ---------------------------------------------------

            for (int k = 0; k < Charges.count; k++) {
                double r = 6;
                for (int i = 0; i < 24; i++) {
                    double a = i * (2 * Math.PI / 24);
                    double px = r * Math.sin(a);
                    double py = r * Math.cos(a);
                    double npx = 0;
                    double npy = 0;
                    int kx, ky;
                    int x = Charges.arr[k].x;
                    int y = Charges.arr[k].y;
                    //g2d.setColor(Color.CYAN);
                    //g2d.fillRect(x + (int)px, y + (int)py, 3, 3);
                    g2d.setColor(Color.BLACK);
                    double kof = 0.05;
                    if (Charges.arr[k].v < 0) {
                        kof = -0.05;
                    }
                    for (int j = 0; j < 10000; j++) {
                        kx = x + (int) px;
                        ky = y + (int) py;
                        if ((kx > 0) && (kx < this.getWidth()) && (ky > 0) && (ky < this.getHeight())) {
                            npx = px + kof * vfieldx[kx][ky] / Math.sqrt(sq(vfieldx[kx][ky]) + sq(vfieldy[kx][ky]));
                            npy = py + kof * vfieldy[kx][ky] / Math.sqrt(sq(vfieldx[kx][ky]) + sq(vfieldy[kx][ky]));
                        } else {
                            break;
                        }
                        if (((int) px != (int) npx) || ((int) py != (int) npy)) {
                            if (Charges.isCTC(kx, ky, 2)) {
                                break;
                            }
                            Buf.setRGB(kx, ky, 0);
                        }
                        //g2d.drawLine(x + (int)px, y + (int)py, x + (int)npx, y + (int)npy);
                        px = npx;
                        py = npy;
                    }
                }
            }

            // ---------------------------------------------------
            redrawAll = false;
        } else {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        }

        for (int i = 0; i < Charges.count; i++) {
            g2d.setColor(Color.BLACK);
            g2d.drawOval(Charges.arr[i].x - 2, Charges.arr[i].y - 2, 4, 4);
            if (Charges.arr[i].selected) {
                g2d.setColor(Color.YELLOW);
            } else if (Charges.arr[i].v > 0) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.BLUE);
            }
            g2d.fillOval(Charges.arr[i].x - 2, Charges.arr[i].y - 2, 4, 4);
        }

        //g.setPaint(Color.GREEN);
        if (tester) {
            g2d.drawRect(100, 100, 80, 20);
            g2d.setColor(Color.yellow);
            //g2d.drawString("Привет мир", 150, 150);
            g2d.setColor(Color.BLUE);
        }

        g.drawImage(Buf, 0, 0, this);

        //super.repaint();
    }

    Scr() {
        //Buf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

}
