package graphics;

import graphics.SortDisplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import sorts.*;
import sorts.business.SortInfo;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

	private int													numElts	= 100;

	@SuppressWarnings("unchecked")
	private static final ObservableList<Class<? extends Sort>>	sorts	= FXCollections.observableArrayList(
																				BozoSort.class, BubbleSort.class,
																				CocktailSort.class, CombSort.class,
																				Shellsort.class,
																				WilliamsHeapsort.class,
																				FloydsHeapsort.class, Smoothsort.class,
																				StoogeSort.class, InsertionSort.class,
																				GnomeSort.class,
																				BinaryInsertionSort.class,
																				SelectionSort.class, Quicksort.class,
																				QuicksortMiddlePivot.class,
																				ParallelQuicksort.class,
																				ParallelQuicksortMiddlePivot.class );

	private ListView<Class<? extends Sort>>						left, right;

	private SortDisplay											sdl		= new SortDisplay(), sdr = new SortDisplay();

	// private Stage stage;
	private Scene												scene;
	private Stage												stage;

	private Button												go, stop, reset;
	private TextField											elements, speed;
	private Label												elts, spd, pRand;

	private CheckBox											partialRandom;

	private boolean												isStop	= false;

	@Override
	public void start ( Stage stage ) throws Exception {
		this.stage = stage;
		elements = new TextField( "100" );
		elts = new Label( "Elements" );
		speed = new TextField( "20" );
		spd = new Label( "Speed (per sec.)" );

		pRand = new Label( "Mostly Sorted ?" );
		partialRandom = new CheckBox();

		// this.stage = stage;
		left = new ListView<>();
		left.setItems( sorts );
		left.setCellFactory( cf );
		left.getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
		left.getSelectionModel().selectedItemProperty().addListener( ( observable, oldValue, newValue ) -> {
			// System.out.println("LOLL");
				sdl.setSelection( newValue );
			} );
		left.getSelectionModel().select( 0 );

		right = new ListView<>();
		right.setItems( sorts );
		right.setCellFactory( cf );
		right.getSelectionModel().setSelectionMode( SelectionMode.SINGLE );
		right.getSelectionModel().selectedItemProperty().addListener( ( observable, oldValue, newValue ) -> {
			// System.out.println("LOLR");
				sdr.setSelection( newValue );
			} );
		right.getSelectionModel().select( 0 );

		go = new Button( "Start" );
		go.setPrefSize( 180, 32 );

		stop = new Button( "Stop" );
		stop.setPrefSize( 180, 32 );
		stop.setDisable( true );

		reset = new Button( "Randomize" );
		reset.setPrefSize( 180, 32 );

		Group group = new Group( left, right, sdl, sdr, go, stop, reset, spd, speed, elts, elements, pRand,
				partialRandom );

		// group.getChildren().addAll(sdl, sdr, button);

		reset.setOnAction( e -> {
			if ( reset.getText().equals( "Randomize" ) ) {
				createArray();
				sdl.reset();
				sdr.reset();
				sdl.redraw();
				sdr.redraw();
			} else {
				thread.configure( true, sdl, sdr );
				thread.cancel();
				createArray();
				sdl.reset();
				sdr.reset();
				stop.setDisable( true );
				left.setDisable( false );
				right.setDisable( false );
				reset.setText( "Randomize" );
				go.setText( "Start" );
				sdl.redraw();
				sdr.redraw();
			}
		} );

		go.setOnAction( e -> {
			if ( go.getText().equals( "Start" ) ) {
				thread = new SortThread();
				thread.configure( isStop, sdl, sdr );
				isStop = false;
				reset.setText( "Reset" );
				stop.setDisable( false );
				thread.start();
			} else if ( go.getText().equals( "Pause" ) ) {
				thread.pause();
				go.setText( "Unpause" );
			} else {
				thread.unpause();
				go.setText( "Pause" );
			}
		} );

		stop.setOnAction( e -> {
			thread.cancel();

			thread.configure( true, sdl, sdr );

			isStop = true;

			stop.setDisable( true );
			reset.setText( "Randomize" );
			go.setText( "Start" );
		} );

		createArray();

		Scene scene = new Scene( group, 1400, 800 );
		this.scene = scene;
		// stage.resizableProperty().addListener(e -> {
		// sdl.setPrefSize(scene.getWidth(), scene.getHeight());
		// });

		// sd.prefWidthProperty().bind(scene.widthProperty());
		// sd.prefHeightProperty().bind(scene.heightProperty());

		stage.setOnCloseRequest( ( e ) -> System.exit( 0 ) );

		scene.widthProperty().addListener( e -> {
			resizeElements();
		} );

		scene.heightProperty().addListener( e -> {
			resizeElements();
		} );

		stage.setScene( scene );
		stage.show();

		resizeElements();

		scene.setFill( Color.LIGHTBLUE );
	}

	private void createArray () {
		try {
			numElts = Integer.valueOf( elements.getText() );
		} catch ( NumberFormatException e ) {
			new ModalDialog( stage, "Invalid number of elements." );
		}

		int[] arr;

		if ( partialRandom.isSelected() ) {
			arr = SortDisplay.generatePartiallySortedArray( numElts );
		} else {
			arr = SortDisplay.generateArray( numElts );
		}

		sdl.configure( arr );
		sdr.configure( arr );
	}

	private static final int	top	= 30, mid = 46;

	private void resizeElements () {
		elts.setLayoutX( 6 );
		elts.setLayoutY( 5 );
		elements.setLayoutX( 66 );
		elements.setLayoutY( 2 );
		spd.setLayoutX( 236 );
		spd.setLayoutY( 5 );
		speed.setLayoutX( 336 );
		speed.setLayoutY( 2 );

		pRand.setLayoutX( 506 );
		pRand.setLayoutY( 5 );
		partialRandom.setLayoutX( 606 );
		partialRandom.setLayoutY( 5 );

		left.setLayoutY( top );
		right.setLayoutY( top );

		left.setPrefSize( scene.getWidth() / 2, scene.getHeight() / 6 );
		right.setLayoutX( scene.getWidth() / 2 );
		right.setPrefSize( scene.getWidth() / 2, scene.getHeight() / 6 );
		sdl.setPrefSize( scene.getWidth() / 2, scene.getHeight() * 5 / 6 - top - mid );
		sdl.setLayoutY( scene.getHeight() / 6 + top + mid );
		sdr.setPrefSize( scene.getWidth() / 2, scene.getHeight() * 5 / 6 - top - mid );
		sdr.setLayoutY( scene.getHeight() / 6 + top + mid );
		sdr.setLayoutX( scene.getWidth() / 2 );

		go.setLayoutX( (scene.getWidth() - reset.getWidth()) / 2 - stop.getWidth() - 8 );
		go.setLayoutY( top + left.getPrefHeight() + (mid - go.getHeight()) / 2 );
		stop.setLayoutX( (scene.getWidth() - stop.getWidth()) / 2 );
		stop.setLayoutY( top + left.getPrefHeight() + (mid - go.getHeight()) / 2 );
		reset.setLayoutX( (scene.getWidth() - reset.getWidth()) / 2 + reset.getWidth() + 8 );
		reset.setLayoutY( top + left.getPrefHeight() + (mid - go.getHeight()) / 2 );
	}

	private Callback<ListView<Class<? extends Sort>>, ListCell<Class<? extends Sort>>>	cf	= new Callback<ListView<Class<? extends Sort>>, ListCell<Class<? extends Sort>>>() {

																								@Override
																								public ListCell<Class<? extends Sort>> call (
																										ListView<Class<? extends Sort>> p ) {

																									ListCell<Class<? extends Sort>> cell = new ListCell<Class<? extends Sort>>() {

																										@Override
																										protected void updateItem (
																												Class<? extends Sort> t,
																												boolean bln ) {
																											super.updateItem(
																													t,
																													bln );

																											String name = "";
																											if ( t != null ) {
																												try {
																													name = t.getAnnotation(
																															SortInfo.class )
																															.name();
																												} catch ( Exception e ) {
																													name = t.getSimpleName();
																												} finally {
																													setText( name );
																												}
																											}
																										}
																									};

																									return cell;
																								}
																							};

	private SortThread																	thread;

	private class SortThread extends Thread {

		private Lock		lock	= new ReentrantLock();
		private Condition	cond	= lock.newCondition();
		private boolean		cont	= false;

		private int			delay;

		private List<SortDisplay>	sds	= new ArrayList<SortDisplay>(), nextSds = new ArrayList<>();

		private void pause () {
			lock.lock();
		}

		private void unpause () {
			lock.unlock();
		}

		private void configure ( boolean val, SortDisplay... sds ) {

			try {
				delay = 1000 / Integer.valueOf( speed.getText() );

				try {
					numElts = Integer.valueOf( elements.getText() );

					left.setDisable( true );
					right.setDisable( true );
					speed.setDisable( true );
					elements.setDisable( true );
					go.setText( "Pause" );
					try {
						// int[] sdlarr, sdrarr;
						// if (val) {
						// sdlarr = sdl.getArray();
						// sdrarr = sdr.getArray();
						// } else {
						// sdlarr = sdrarr = SortDisplay.generateArray(numElts);
						// }

						sdl.setSort( left.getSelectionModel().getSelectedItems().get( 0 ).newInstance() );
						sdr.setSort( right.getSelectionModel().getSelectedItems().get( 0 ).newInstance() );

						// sdl.configure(sdlarr);
						// sdr.configure(sdrarr);

					} catch ( Exception e1 ) {
						e1.printStackTrace();
					}

					this.sds.addAll( Arrays.asList( sds ) );
					this.sds.forEach( sd -> sd.reset() );

					this.cont = true;

				} catch ( NumberFormatException ex2 ) {
					new ModalDialog( stage, "Invalid number of elements." );
				}

			} catch ( NumberFormatException ex1 ) {
				new ModalDialog( stage, "Invalid speed. (Iterations per second)" );
			}

		}

		public void cancel () {
			thread.pause();
			sds.clear();
			nextSds.clear();
			thread.unpause();
		}

		@Override
		public void run () {

			while ( !sds.isEmpty() ) {
				lock.lock();

				nextSds.addAll( sds );

				cont = false;

				Platform.runLater( ( ) -> {
					lock.lock();

					try {
						sds.forEach( sd -> {
							// System.out.println("Run1");
							if ( sd.next() ) {
								// System.out.println("NOPE");
								nextSds.remove( sd );
							}
						} );

						sleep( this.delay );
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}

					sds.clear();
					sds.addAll( nextSds );
					nextSds.clear();

					cont = true;
					cond.signalAll();
					lock.unlock();
				} );

				while ( !cont ) {
					try {
						cond.await();
					} catch ( InterruptedException e ) {
						e.printStackTrace();
					}
				}

				// this.delay = Main.this.delay;

				lock.unlock();

			}
			Platform.runLater( ( ) -> {
				reset();
			} );
		}
	}

	private void reset () {
		// System.out.println("RESET");
		go.setText( "Start" );
		reset.setText( "Randomize" );
		left.setDisable( false );
		right.setDisable( false );
		speed.setDisable( false );
		elements.setDisable( false );
	}

	public static void main ( String[] args ) {
		launch( args );
	}

}
