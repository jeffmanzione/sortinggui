package com.jeffmanzione.sortinggui.graphics;

import com.jeffmanzione.sortinggui.graphics.exceptions.UninitializedSortDisplayException;
import com.jeffmanzione.sortinggui.sorts.Sort;
import com.jeffmanzione.sortinggui.sorts.SortCompletedException;
import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class SortDisplay extends Pane {

  private int[] startArray;
  private int[] heat;
  private Sort sort;

  private Canvas canvas;

  private GraphicsContext g;

  private Label comparisons;

  private Hyperlink link;

  private String url;

  private static final int Bar_Height = 36;

  public SortDisplay() {
    comparisons = new Label("Comparisons: 0    Swaps: 0");
    link = new Hyperlink("EMPTY");
    link.setDisable(true);

    link.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent e) {
        try {
          Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });

    Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

    canvas = new Canvas(primaryScreenBounds.getWidth(),
                        primaryScreenBounds.getHeight());

    this.widthProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable,
                          Number oldValue, Number newValue) {
        redraw();
      }
    });
    this.heightProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable,
                          Number oldValue, Number newValue) {
        redraw();
      }
    });

    g = canvas.getGraphicsContext2D();

    this.getChildren().addAll(canvas, comparisons, link);

    redraw();
  }

  public boolean next() {
    try {
      return sort.next();
    } catch (SortCompletedException e) {
      return true;
    } finally {
      redraw();
    }
  }

  public void redraw() {
    if (startArray != null) {
      g.setFill(Color.AZURE);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      double top = Bar_Height;
      double width = this.getWidth() - 2;
      double height = this.getHeight() - 2 - top;

      int[] vals = new int[startArray.length];

      int max = Integer.MIN_VALUE;
      int min = Integer.MAX_VALUE;

      for (int i = 0; i < vals.length; i++) {
        vals[i] = sort == null ? startArray[i] : sort.valueOf(i);

        if (vals[i] < min) {
          min = vals[i];
        }

        if (vals[i] > max) {
          max = vals[i];
        }
      }

      double blockWidth = width / vals.length;
      if (blockWidth < 3) {
        blockWidth = 3;
      }

      List<Integer> lastCompared =
          sort == null ? new ArrayList<Integer>() : sort.lastCompared();
      List<Integer> lastSwapped =
          sort == null ? new ArrayList<Integer>() : sort.lastSwapped();

      boolean isDone = sort == null ? false : sort.isDone();

      for (int i = 0; i < heat.length; i++) {
        if (heat[i] > 0) {
          heat[i]--;
        }
      }

      for (int i : lastSwapped) {
        heat[i] += 2;
      }
      for (int i : lastCompared) {
        heat[i] += 2;
      }

      for (int i = 0; i < vals.length; i++) {
        if (isDone) {
          g.setFill(Color.GREEN);
        } else if (lastSwapped.contains(i)) {
          g.setFill(Color.BLUE);
        } else if (lastCompared.contains(i)) {
          g.setFill(Color.RED);
        } else {
          g.setFill(Color.color(Math.min(((double)heat[i]) / 15, 1.0),
                                1.0 - Math.min(((double)heat[i]) / 15, 1.0),
                                1.0));
        }
        g.fillRect(i * blockWidth + 1,
                   top + height + 1 - vals[i] * height / max, blockWidth - 2,
                   vals[i] * height / max);

        g.setFill(Color.BLACK);

        g.fillRect(i * blockWidth + 1,
                   top + height + 1 - vals[i] * height / max, blockWidth - 2,
                   height / max);
      }
    }

    String name = "";
    try {
      if (sort == null) {
        name = "";
      } else {
        name = sort.getClass().getAnnotation(SortInfo.class).name();
      }
    } catch (Exception e) {
      name = sort.getClass().getSimpleName();
    }

    g.setStroke(Color.color(1, 1, 1, 0.5));
    g.setLineWidth(5);
    g.strokeText(name, 4, g.getFont().getSize());
    g.setFont(Font.font("Calibri Light", FontWeight.BOLD, 20));
    g.setFill(Color.RED);
    g.fillText(name, 4, g.getFont().getSize());

    g.setFill(Color.LIGHTGREY);
    g.fillRect(0, 0, this.getWidth(), Bar_Height);

    g.setLineWidth(1);
    g.setStroke(Color.GRAY);
    g.strokeRect(1, 1, this.getWidth() - 1, this.getHeight() - 1);

    comparisons.setText(
        "Comparisons: " + (sort == null ? 0 : sort.getComparisons()) +
        "    Swaps: " + (sort == null ? 0 : sort.getSwaps()));
    comparisons.setLayoutY(10);
    comparisons.setLayoutX(this.getWidth() / 2 - comparisons.getWidth() / 2);
    link.setLayoutY(6);
    link.setLayoutX(10);
  }

  public void configure(int[] startArray) {
    this.startArray = startArray;
    this.heat = new int[startArray.length];
  }

  public void reset() {
    if (startArray == null) {
      throw new UninitializedSortDisplayException();
    } else {
      if (sort != null) {
        sort.initialize(startArray);
      }
    }
  }

  public void setSort(Sort sort) { this.sort = sort; }

  public static int[] generateArray(int length) {
    Integer[] valt = new Integer[length];
    for (int i = 0; i < length; i++) {
      valt[i] = i + 1;
    }

    List<Integer> valsList = Arrays.asList(valt);
    Collections.shuffle(valsList);

    final int[] vals = new int[valt.length];
    for (int i = 0; i < vals.length; i++) {
      vals[i] = valsList.get(i);
    }

    return vals;
  }

  public int[] getArray() { return sort.getArray(); }

  public boolean isSorted() { return sort.isDone(); }

  public static int[] generatePartiallySortedArray(int length) {
    int[] vals = new int[length];
    Integer[] seen = new Integer[length];
    for (int i = 0; i < length; i++) {
      vals[i] = i + 1;
      seen[i] = i;
    }

    List<Integer> valsLeft = new ArrayList<>(Arrays.asList(seen));

    Random rand = new Random();

    for (int i = 0; i < length / 4; i++) {
      int length1 = valsLeft.size();
      int index1 = rand.nextInt(length1);
      int index2 = index1;
      while (index2 == index1) {
        int offset, base;

        base = (length1 / 8);
        offset = index1;

        if (offset - base + length1 / 4 > length1 - 1) {
          offset = length1 - 1 + base - length1 / 4;
        }

        if (index1 - (length1 / 8) < 1) {
          offset = base = 0;
        }

        index2 = offset + rand.nextInt(length1 / 4) - base;
      }

      int tmp = vals[index1];
      vals[index1] = vals[index2];
      vals[index2] = tmp;

      valsLeft.remove(index1);
      valsLeft.remove(index2);
    }

    return vals;
  }

  public void setSelection(Class<? extends Sort> sortClass) {
    String name = "";

    try {
      name = sortClass.getAnnotation(SortInfo.class).name();
      url = sortClass.getAnnotation(SortInfo.class).link();
      link.setDisable(false);
    } catch (Exception e) {
      name = sortClass.getSimpleName();
      link.setDisable(true);
    } finally {
      this.link.setText(name);
    }
  }
}
