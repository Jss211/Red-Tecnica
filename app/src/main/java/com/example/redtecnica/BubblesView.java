package com.example.redtecnica;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.Random;

public class BubblesView extends View {
    private Paint paint = new Paint();
    private ArrayList<Bubble> bubbles = new ArrayList<>();
    private Random random = new Random();

    public BubblesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Creamos 15 burbujas con un rojo muy leve (alfa bajo)
        paint.setColor(0x22D32F2F); // El '22' es la transparencia (rojo leve)
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bubbles.isEmpty()) {
            for (int i = 0; i < 15; i++) {
                bubbles.add(new Bubble(getWidth(), getHeight()));
            }
        }

        for (Bubble b : bubbles) {
            b.update(getWidth(), getHeight());
            canvas.drawCircle(b.x, b.y, b.radius, paint);
        }
        invalidate(); // Esto crea el bucle de animación
    }

    private class Bubble {
        float x, y, radius, speed;
        Bubble(int w, int h) {
            radius = random.nextInt(100) + 50;
            x = random.nextInt(w);
            y = random.nextInt(h);
            speed = random.nextFloat() * 2 + 0.5f; // Movimiento lento
        }
        void update(int w, int h) {
            y -= speed; // Flotan hacia arriba
            if (y + radius < 0) {
                y = h + radius;
                x = random.nextInt(w);
            }
        }
    }
}