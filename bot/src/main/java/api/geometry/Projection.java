/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package api.geometry;

import internals.RSClient;
import types.shapes.ExtPolygon;
import types.tile.LocalTile;

import static java.lang.Math.PI;

public class Projection {


    public static final int MAX_Z = 4;
    public static final int LOCAL_COORD_BITS = 7;
    public static final int SCENE_SIZE = 104;
    public static final int TILE_SIZE = 128;
    public static final int HALF_TILE_SIZE = 128 >> 2;
    public static final int[] SINE = new int[2048];
    public static final int[] COSINE = new int[2048];

    public static double RS_PI = PI / 1024.0D;

    static {
        for (int i = 0; i < 2048; ++i) {
            SINE[i] = (int) (65536.0D * Math.sin((double) i * RS_PI));
            COSINE[i] = (int) (65536.0D * Math.cos((double) i * RS_PI));
        }
    }

    public static int getTileHeight(int localX, int localY, int plane) {
        int sceneX = (localX >> LOCAL_COORD_BITS);
        int sceneY = (localY >> LOCAL_COORD_BITS);

        if (sceneX >= 0 && sceneY >= 0 && sceneX < SCENE_SIZE && sceneY < SCENE_SIZE) {
            byte[][][] tileSettings = RSClient.tileSettings();
            int[][][] tileHeights = RSClient.tileHeights();

            int z1 = plane;
            if (plane < MAX_Z - 1 && (tileSettings[1][sceneX][sceneY] & 2) == 2) {
                z1 = plane + 1;
            }

            int x = (localX & (TILE_SIZE - 1));
            int y = (localY & (TILE_SIZE - 1));
            int var8 = ((x * tileHeights[z1][sceneX + 1][sceneY]) + ((TILE_SIZE - x) * tileHeights[z1][sceneX][sceneY])) >> LOCAL_COORD_BITS;
            int var9 = ((tileHeights[z1][sceneX][sceneY + 1] * (TILE_SIZE - x)) + (x * tileHeights[z1][sceneX + 1][sceneY + 1])) >> LOCAL_COORD_BITS;
            return (((TILE_SIZE - y) * var8) + (y * var9)) >> LOCAL_COORD_BITS;
        }

        return 0;
    }

    public static int getTileHeight(LocalTile localTile, int plane) {
        return getTileHeight(localTile.x, localTile.y, plane);
    }

    public static ExtPolygon tileToCanvas(LocalTile localTile) {
        final int[] x3d = new int[]{-HALF_TILE_SIZE - 32, HALF_TILE_SIZE + 32, HALF_TILE_SIZE + 32, -HALF_TILE_SIZE - 32};
        final int[] y3d = new int[]{-HALF_TILE_SIZE - 32, -HALF_TILE_SIZE - 32, HALF_TILE_SIZE + 32, HALF_TILE_SIZE + 32};
        final int[] z3d = new int[4];

        return pointsToCanvas(x3d, y3d, z3d, 4, 0, -1, -1, localTile, getTileHeight(localTile, RSClient.plane()));
    }

    public static ExtPolygon pointsToCanvas(int[] x3d, int[] y3d, int[] z3d, int count, int rotate, int scaleWidth, int scaleHeight, LocalTile localTile, int tileHeight) {
        final int
                cameraPitch = RSClient.cameraPitch(),
                cameraYaw = RSClient.cameraYaw(),

                pitchSin = SINE[cameraPitch],
                pitchCos = COSINE[cameraPitch],
                yawSin = SINE[cameraYaw],
                yawCos = COSINE[cameraYaw],
                rotateSin = SINE[rotate],
                rotateCos = COSINE[rotate],

                cx = localTile.x - RSClient.cameraX(),
                cy = localTile.y - RSClient.cameraY(),
                cz = tileHeight - RSClient.cameraZ(),

                viewportXMiddle = RSClient.viewportWidth() / 2,
                viewportYMiddle = RSClient.viewportHeight() / 2,
                viewportXOffset = RSClient.viewportX(),
                viewportYOffset = RSClient.viewportY(),

                zoom3d = RSClient.viewportZoom();

        int[] tempX = new int[count];
        int[] tempY = new int[count];
        int c = 0;

        boolean scale = scaleWidth != -1 && scaleHeight != -1 && scaleWidth != 128 && scaleHeight != 128;

        for (int i = 0; i < count; i++) {
            int x = x3d[i];
            int y = y3d[i];
            int z = z3d[i];

            if (scale) {
                x = (x * scaleWidth) >> 7;
                y = (y * scaleHeight) >> 7;
                z = (z * scaleWidth) >> 7;
            }

            if (rotate != 0) {
                int x0 = x;
                x = x0 * rotateCos + y * rotateSin >> 16;
                y = y * rotateCos - x0 * rotateSin >> 16;
            }

            x += cx;
            y += cy;
            z += cz;

            final int
                    x1 = x * yawCos + y * yawSin >> 16,
                    y1 = y * yawCos - x * yawSin >> 16,
                    y2 = z * pitchCos - y1 * pitchSin >> 16,
                    z1 = y1 * pitchCos + z * pitchSin >> 16;

            if (z1 >= 50) {
                tempX[c] = (viewportXMiddle + x1 * zoom3d / z1) + viewportXOffset;
                tempY[c] = (viewportYMiddle + y2 * zoom3d / z1) + viewportYOffset;
                c++;
            }

        }

        return new ExtPolygon(tempX, tempY, c);
    }


}
