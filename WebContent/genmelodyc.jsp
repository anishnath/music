<!DOCTYPE html>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Generate music sheet music score from midi file">
    <meta name="keywords" content=" ">
    <meta name="author" content="Anish nath">
    <meta name="robots" content="index,follow" />
	<meta name="googlebot" content="index,follow" />
	<meta name="resource-type" content="document" />
	<meta name="classification" content="tools" />
	<meta name="language" content="en" />
	
	<script src="https://cdn.jsdelivr.net/npm/@magenta/music@^1.2.3"></script>
	<script src="js/jquery.min.js"></script>
	 

    <title>Generate chords and play along </title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/blog-post.css" rel="stylesheet">
    
    
   
	

	

	
	
		<!-- JSON-LD markup generated by Google Structured Data Markup Helper. -->
<!-- JSON-LD markup generated by Google Structured Data Markup Helper. -->
<script type="application/ld+json">
{
  "@context" : "http://schema.org",
  "@type" : "Article",
  "mainEntityOfPage": {
    "@type": "WebPage",
    "@id": "http://notemusic.site/genmelodya.jsp"
  },
  "name" : "Generate or find new chords",
  "author" : {
    "@type" : "Person",
    "name" : "Anish"
  },
  "headline": "Generate or find new chords",
  "image":"http://notemusic.site/images/1.png",
  "datePublished" : "2019-06-21",
  "dateModified": "2019-06-21",
  "articleSection" : "Generate or find new chords",
  "articleBody" : [ "Generate or find new chords"],
  "publisher" : {
    "@type" : "Organization",
    "name" : "notemusic.site ",
    "logo" :  {
      "@type": "ImageObject",
      "url": "http://notemusic.site/genmelodyc.jsp"
    }
  }
}
</script>

<%@ include file="analytics.jsp"%>




  </head>

  <body>
  
  

  
  

    <!-- Navigation -->
 	<%@ include file="navigation.jsp"%>

    <!-- Page Content -->
    <div class="container">
    
    
    

      <div class="row">

        <!-- Post Content Column -->
        <div class="col-lg-8">

          <!-- Title -->
          <h2 class="mt-4">Generate/Find new chords and view the music score</h2>
          

	
		
		
		<div id='message'>Loading model...</div>
		
		<div id='stepchordsprogessrions'>
		<label for="customRange1"><strong>Step To Played for each Chord</strong></label>
		<input type="range" value="4" class="custom-range" min="1" max="8" step="1" id="stepchordsprogression">
		</div>
		
		<div id='chordsprogessrion'>
		<label for="customRange1"><strong>Repeat chord progression.</strong></label>
		<input type="range" value="2" class="custom-range" min="0" max="15" step="1" id="repeatchordsprogression">
		</div>
		
    <div id='chords'>
      <label for="customRange1"><strong>Choose Chords.</strong></label>
        <input class="form-control-row" id='chord1' size="3" type='text' value='C'>
        <input class="form-control-row" id='chord2' size="3" type='text' value='Gm'>
        <input class="form-control-row" id='chord3' size="3" type='text' value='Am'>
        <input class="form-control-row" id='chord4' size="3" type='text' value='F'>
        <input class="form-control-row" id='chord5' size="3" type='text' value='C'>
    </div>
    
    <br>
    <input id='play' class="btn btn-primary" name="play" type='button' value='Play' disabled>
    <input id='showscore' class="btn btn-primary" name="Show Score" type='button' value='Show Score' disabled>
    <input id='midi' class="btn btn-primary" name="Download Midi" type='button' value='Download Midi' disabled>
				

 <div id="output">
    
    </div>
    
        <div class="container">
        <h3>Piano Roll</h3>
      <canvas id="canvas2"></canvas>
    </div>
    
        <div class="container">
      <canvas id="canvas"></canvas>
    </div>
				

				<!-- Author -->
         <!--  <p class="lead">
            by
            <a href="https://www.linkedin.com/in/anishnath">Anish</a>
            <p>Posted on Tuesday August 15, 2018</p>
          </p> -->
          
         <!--  <img class="img-fluid rounded" src="img/kube-nginx.png" height="400" width="500" alt="Referefce "> -->
          
          <%@ include file="footer_adsense.jsp"%>
           <%@ include file="analytics.jsp"%>
           
     
	
	

          
       




<%@ include file="thanks.jsp"%>


<hr>


      <!-- Comments Form -->
    

          <!-- Single Comment -->
 

          <!-- Comment with nested comments -->
        
          

        </div>

		
        
        <!-- Sidebar Widgets Column -->
        <div class="col-md-4">

          <!-- Ad Widget -->
         <%@ include file="footer_adsense.jsp"%> 

          
         
         
         <!-- Topic Widget -->
         <%@ include file="side.jsp"%>
         
           <!-- Add Comments Support -->
         
         
         

        </div>

      </div>
      <!-- /.row -->


	<%@ include file="addcomments.jsp"%>
    
    </div>
    
    
    
    
    <!-- /.container -->

    <!-- Footer -->
    <footer class="py-5 bg-dark">
      <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; notemusic.site 2019</p>
      </div>
      <!-- /.container -->
    </footer>
    

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

 <script>
    
 // Number of steps to play each chord.
    STEPS_PER_CHORD = 4;
    STEPS_PER_PROG = 4 * STEPS_PER_CHORD;

    // Number of times to repeat chord progression.
    NUM_REPS = 2;

    // Set up Improv RNN model and player.
    const model = new mm.MusicRNN('https://storage.googleapis.com/magentadata/js/checkpoints/music_rnn/chord_pitches_improv');
    const player = new mm.Player();
    var visualizer, viz, vizPLayer;



    var playing = false;

    // Current chords being played.
    var currentChords = undefined;
    var seq = undefined;
    var file = undefined;

    // Sample over chord progression.
    const playOnce = () => {
    	

    	NUM_REPS = document.getElementById('repeatchordsprogression').value
    	STEPS_PER_CHORD = document.getElementById('stepchordsprogression').value
    	

    	
       	
        const chords = currentChords;
      
      // Prime with root note of the first chord.
      const root = mm.chords.ChordSymbols.root(chords[0]);
        seq = { 
        quantizationInfo: {stepsPerQuarter: 4},
        notes: [],
        totalQuantizedSteps: 1
      };  
      
      document.getElementById('message').innerText = 'Improvising over: ' + chords;
      model.continueSequence(seq, STEPS_PER_PROG + (NUM_REPS-1)*STEPS_PER_PROG - 1, 0.9, chords)
        .then((contSeq) => {
          // Add the continuation to the original.
          contSeq.notes.forEach((note) => {
            note.quantizedStartStep += 1;
            note.quantizedEndStep += 1;
            seq.notes.push(note);
          });
        
          const roots = chords.map(mm.chords.ChordSymbols.root);
         
          for (var i=0; i<NUM_REPS; i++) { 
        	  
        	  
        	  
            // Add the bass progression.
            seq.notes.push({
              instrument: 1,
              program: 32,
              pitch: 36 + roots[0],
              quantizedStartStep: i*STEPS_PER_PROG,
              quantizedEndStep: i*STEPS_PER_PROG + STEPS_PER_CHORD
            });
            seq.notes.push({
              instrument: 1,
              program: 32,
              pitch: 36 + roots[1],
              quantizedStartStep: i*STEPS_PER_PROG + STEPS_PER_CHORD,
              quantizedEndStep: i*STEPS_PER_PROG + 2*STEPS_PER_CHORD
            });
            seq.notes.push({
              instrument: 1,
              program: 32,
              pitch: 36 + roots[2],
              quantizedStartStep: i*STEPS_PER_PROG + 2*STEPS_PER_CHORD,
              quantizedEndStep: i*STEPS_PER_PROG + 3*STEPS_PER_CHORD
            });
            seq.notes.push({
              instrument: 1,
              program: 32,
              pitch: 36 + roots[3],
              quantizedStartStep: i*STEPS_PER_PROG + 3*STEPS_PER_CHORD,
              quantizedEndStep: i*STEPS_PER_PROG + 4*STEPS_PER_CHORD
            });        
          }
        
          // Set total sequence length.
          seq.totalQuantizedSteps = STEPS_PER_PROG * NUM_REPS;
         
          config = {
        		  noteHeight: 6,
        		  pixelsPerTimeStep: 30,  // like a note width
        		  noteSpacing: 1,
        		  noteRGB: '8, 41, 64',
        		  activeNoteRGB: '240, 84, 119',
        		}
          
          viz = new mm.PianoRollCanvasVisualizer(seq, document.getElementById('canvas2'),config);
         // visualizer = new mm.Visualizer(seq, document.getElementById('canvas'));
         

           
          vizPlayer = new mm.Player(false, {
        	    run: (note) => viz.redraw(note),
        	    stop: () => {}
        	  });
 		 
   
          
        
           // Play it!
          vizPlayer.start(seq,120).then(() => {
            playing = false;
            document.getElementById('message').innerText = 'Change chords and play again!';
            checkChords();
          } 
          

          
          );
        })
    }  
    
    

    // Check chords for validity and highlight invalid chords.
    const checkChords = () => {
      const chords = [
        document.getElementById('chord1').value,
        document.getElementById('chord2').value,
        document.getElementById('chord3').value,
        document.getElementById('chord4').value,
        document.getElementById('chord5').value
      ]; 
     
      const isGood = (chord) => {
        if (!chord) {
          return false;
        }
        try {
          mm.chords.ChordSymbols.pitches(chord);
          return true;
        }
        catch(e) {
          return false;
        }
      }
      
      var allGood = true;
      if (isGood(chords[0])) {
        document.getElementById('chord1').style.color = 'black';
      } else {
        document.getElementById('chord1').style.color = 'red';
        allGood = false;
      }
      if (isGood(chords[1])) {
        document.getElementById('chord2').style.color = 'black';
      } else {
        document.getElementById('chord2').style.color = 'red';
        allGood = false;
      }
      if (isGood(chords[2])) {
        document.getElementById('chord3').style.color = 'black';
      } else {
        document.getElementById('chord3').style.color = 'red';
        allGood = false;
      }
      if (isGood(chords[3])) {
        document.getElementById('chord4').style.color = 'black';
      } else {
        document.getElementById('chord4').style.color = 'red';
        allGood = false;
      }
      
      if (isGood(chords[4])) {
          document.getElementById('chord5').style.color = 'black';
        } else {
          document.getElementById('chord5').style.color = 'red';
          allGood = false;
        }
      
      var changed = false;	
      if (currentChords) {
        if (chords[0] !== currentChords[0]) {changed = true;}
        if (chords[1] !== currentChords[1]) {changed = true;}
        if (chords[2] !== currentChords[2]) {changed = true;}
        if (chords[3] !== currentChords[3]) {changed = true;}  
        if (chords[4] !== currentChords[4]) {changed = true;}  
      }
      else {changed = true;}
      document.getElementById('play').disabled = !allGood || (!changed && playing);
    }

    // Initialize model then start playing.
    model.initialize().then(() => {
      document.getElementById('message').innerText = 'Loaded...'
      document.getElementById('play').disabled = false;
    });

    // Play when play button is clicked.
    document.getElementById('play').onclick = () => {
      playing = true;
      document.getElementById('midi').disabled = false;
      document.getElementById('showscore').disabled = false;
      currentChords = [
        document.getElementById('chord1').value,
        document.getElementById('chord2').value,
        document.getElementById('chord3').value,
        document.getElementById('chord4').value, 
        document.getElementById('chord5').value    
      ];
      
      mm.Player.tone.context.resume();
      player.stop();
      playOnce();
      $('#output').empty();
      player.stop();
    }
    
    
 // Play when play button is clicked.
    document.getElementById('midi').onclick = () => {
      playing = true;
      document.getElementById('midi').disabled = true;
      
		var midi = mm.sequenceProtoToMidi(seq); // pass your byte response to this constructor
      
      //alert(midi)
      file = new Blob([midi], {type: 'audio/midi'});
		
	   
      
      if (window.navigator.msSaveOrOpenBlob) {
  	    window.navigator.msSaveOrOpenBlob(file, 'interp.mid');
  	  } else { // Others
  	    const a = document.createElement('a');
  	    const url = URL.createObjectURL(file);
  	    a.href = url;
  	    a.download = 'interp.mid';
  	    document.body.appendChild(a);
  	    a.click();
  	    setTimeout(() => {
  	      document.body.removeChild(a);
  	      window.URL.revokeObjectURL(url);  
  	    }, 0); 
  	  }
    }
    
 // Play when play button is clicked.
    document.getElementById('showscore').onclick = () => {
      playing = true;
      document.getElementById('showscore').disabled = true;
      
      
      
      var midi = mm.sequenceProtoToMidi(seq); // pass your byte response to this constructor
      
      //alert(midi)
      file = new Blob([midi], {type: 'audio/midi'});
      
      
      
      
      $.ajax({
          type: "POST",
          enctype: 'multipart/form-data',
          url: "GenerateMelodyC",
          data: file,
          processData: false,
          contentType: false,
          cache: false,
          timeout: 600000,
          success: function (data) {

        	  $('#output').empty();
		      $('#output').append(data);
		      //console.log(data);

          },
          error: function (e) {

              $("#output").text(e.responseText);
              console.log("ERROR : ", e);
              $("#btnSubmit").prop("disabled", false);

          }
      }); 
    }

    // Check chords for validity when changed.
    document.getElementById('chord1').oninput = checkChords;
    document.getElementById('chord2').oninput = checkChords;
    document.getElementById('chord3').oninput = checkChords;
    document.getElementById('chord4').oninput = checkChords;
    
    
    
    
    </script>
    
    
    
   

  </body>

</html>