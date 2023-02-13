package com.jeffmanzione.sortinggui.sorts;

import com.jeffmanzione.sortinggui.sorts.business.SortInfo;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@SortInfo(name = "Smoothsort", designer = "Dijkstra (1981)",
          link = "http://en.wikipedia.org/wiki/Smoothsort")
public class Smoothsort extends Sort {

  private int index;
  private boolean buildingHeap = true;
  private Deque<HeapEvent> events;

  private LeonardoHeap leon;

  private int[] leonardo;

  @Override
  protected void initialize() {
    index = 0;

    leon = new LeonardoHeap();

    events = new LinkedList<HeapEvent>() {
      private static final long serialVersionUID = 2580961194841371381L;

      @Override
      public boolean add(HeapEvent e) {
        return super.add(e);
      }

      @Override
      public HeapEvent remove() {
        HeapEvent e = super.remove();
        return e;
      }
    };

    buildLeonardo(2, 1, 1);
    leonardo[0] = leonardo[1] = 1;
  }

  private void buildLeonardo(int iters, int prev2, int prev1) {
    int leon = prev1 + prev2 + 1;

    if (leon > arrayLength()) {
      leonardo = new int[iters + 1];
    } else {
      buildLeonardo(iters + 1, prev1, leon);
    }

    leonardo[iters] = leon;
  }

  @Override
  protected void nextMove() {

    doLoopEvent();

    if (events.isEmpty()) {
      if (buildingHeap) {
        while (events.isEmpty()) {
          incHeap();
        }

      } else {
        decHeap();
      }
      if (this.lastCompared().size() == 0) {
        doLoopEvent();
      }
    }
  }

  private void doLoopEvent() {

    int numCompares = 0;
    while (!events.isEmpty()) {
      HeapEvent event = events.remove();
      if (event instanceof Compare) {
        numCompares++;
        if (numCompares > 1) {
          events.push(event);
          return;
        } else {
          this.compare(event.x, event.y);
        }
      } else {
        this.swap(event.x, event.y);
      }
    }
  }

  private boolean done = false;

  private void decHeap() {
    leon.delete();
    if (index == 1) {
      done = true;
    }
  }

  private void incHeap() {
    leon.insert();

    if (index == arrayLength()) {
      index--;
      buildingHeap = false;
    }
  }

  @Override
  protected boolean doneCheck() {
    return done;
  }

  private abstract class HeapEvent {
    protected int x, y;

    public HeapEvent(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  private class Swap extends HeapEvent {
    public Swap(int x, int y) { super(x, y); }

    public String toString() { return "Swap(" + x + "," + y + ")"; }
  }

  private class Compare extends HeapEvent {
    public Compare(int x, int y) { super(x, y); }

    public String toString() { return "Swap(" + x + "," + y + ")"; }
  }

  private class LeonardoHeap {

    private class LeonardoNode {
      private int order = 0, val = 0;

      private LeonardoNode left, right;

      private int index;

      public LeonardoNode(int index, int val) {
        this.index = index;
        this.val = val;
      }

      public String toString() {
        if (left == null) {
          return "" + val;
        } else {
          return "(" + left + "," + right + "<--" + val + ")";
        }
      }

      public LeonardoNode(int index, int val, LeonardoNode left,
                          LeonardoNode right) {
        this(index, val);
        this.left = left;
        this.right = right;
        this.order = left.order + 1;
      }

      private void reheap() {

        if (left != null) {
          if (left.val > right.val) {
            events.add(new Compare(left.index, this.index));
            if (left.val > this.val) {
              events.add(new Swap(left.index, this.index));
              int tmp = this.val;
              this.val = left.val;
              left.val = tmp;

              left.reheap();
            }
          } else {

            events.add(new Compare(right.index, this.index));
            if (right.val > this.val) {
              events.add(new Swap(right.index, this.index));
              int tmp = this.val;
              this.val = right.val;
              right.val = tmp;

              right.reheap();
            }
          }
        }
      }
    }

    private List<LeonardoNode> children;

    public LeonardoHeap() { children = new ArrayList<>(); }

    public String toString() { return children.toString(); }

    public LeonardoNode last() { return children.get(children.size() - 1); }

    public void delete() {
      LeonardoNode node = children.remove(children.size() - 1);
      if (node.left != null) {
        children.add(node.left);
        children.add(node.right);

        fixUpHeaps();
      }

      index--;
    }

    public void insert() {
      int val = valueOf(Smoothsort.this.index);

      LeonardoNode node;

      boolean shouldInc = false;

      if (children.size() > 1 &&
          last().order + 1 == children.get(children.size() - 2).order) {
        shouldInc = true;
        node = new LeonardoNode(index, val);

      } else if (children.size() == 0 || last().order != 1) {
        node = new LeonardoNode(index, val);
        node.order = 1;
      } else {
        node = new LeonardoNode(index, val);
      }

      children.add(node);

      fixUpHeaps();

      if (shouldInc) {
        this.incHeap();
      }
      index++;
    }

    private void incHeap() {
      LeonardoNode node = children.remove(children.size() - 1);
      LeonardoNode right = children.remove(children.size() - 1);
      LeonardoNode left = children.remove(children.size() - 1);
      node = new LeonardoNode(node.index, node.val, left, right);
      children.add(node);
    }

    private void fixUpHeaps() {
      if (!buildingHeap ||
          (children.size() > 1 &&
           arrayLength() - index <=
               leonardo[(children.get(children.size() - 2).order == 0
                             ? 1
                             : children.get(children.size() - 2).order) -
                        1] +
                   1)) {
        int pos = children.size() - 2;

        boolean doneRoot = false;

        if (pos == 0) {
          pos++;
          doneRoot = true;
        }
        while (pos > 0) {
          LeonardoNode cur = children.get(pos);
          LeonardoNode last = children.get(pos - 1);
          events.add(new Compare(cur.index, last.index));
          if (cur.val < last.val) {
            events.add(new Swap(last.index, cur.index));
            int tmp = cur.val;
            cur.val = last.val;
            last.val = tmp;
            last.reheap();
          }

          pos--;

          if (pos == 0 && !doneRoot) {
            pos = children.size() - 1;
            doneRoot = true;
          }
        }

      } else if (children.size() > 1) {
        LeonardoNode cur = children.get(children.size() - 1);
        LeonardoNode last = children.get(children.size() - 2);
        events.add(new Compare(cur.index, last.index));
        if (cur.val < last.val) {
          events.add(new Swap(last.index, cur.index));
          int tmp = cur.val;
          cur.val = last.val;
          last.val = tmp;
          last.reheap();
        }
      }
    }
  }
}
