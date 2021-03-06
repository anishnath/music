<!DOCTYPE html>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Generate new meldoy from your midi file using ai tensorflow">
    <meta name="keywords" content=" ">
    <meta name="author" content="Anish nath">
    <meta name="robots" content="index,follow" />
	<meta name="googlebot" content="index,follow" />
	<meta name="resource-type" content="document" />
	<meta name="classification" content="tools" />
	<meta name="language" content="en" />

    <title>Generate new melody</title>

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
  "name" : "Generate Music Sheet from MIDI file",
  "author" : {
    "@type" : "Person",
    "name" : "Anish"
  },
  "headline": "Generate Music Sheet from MIDI file",
  "image":"https://8gwifi.org/docs/img/kube-nginx.png",
  "datePublished" : "2019-06-21",
  "dateModified": "2019-06-21",
  "articleSection" : "Generate Music Sheet from MIDI file",
  "articleBody" : [ "Generate Music Sheet from MIDI file"],
  "publisher" : {
    "@type" : "Organization",
    "name" : "8gwifi.org Tech Blog",
    "logo" :  {
      "@type": "ImageObject",
      "url": "http://notemusic.site/genmelodya.jsp"
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

   
   <div class="col-lg-3 col-md-6 mb-4">
        <div class="card h-100">
          <img class="card-img-top" src="images/1.png" alt="">
          <div class="card-body">
            <h4 class="card-title">Midi to Music Score</h4>
            <p class="card-text">Music score is great way of learning and studying music, if you have midi file neeed to know the score, this is for you </p>
          </div>
          <div class="card-footer">
            <a href="genmelodya.jsp" class="btn btn-primary">Find Out More!</a>
          </div>
        </div>
      </div>

      <div class="col-lg-3 col-md-6 mb-4">
        <div class="card h-100">
          <img class="card-img-top" src="images/2.png" alt="">
          <div class="card-body">
            <h4 class="card-title">Parse a MIDI File</h4>
            <p class="card-text">Convert MIDI file to list of notes with length and starting time</p>
          </div>
          <div class="card-footer">
            <a href="parsemidi.jsp" class="btn btn-primary">Find Out More!</a>
          </div>
        </div>
      </div>

      <div class="col-lg-3 col-md-6 mb-4">
        <div class="card h-100">
          <img class="card-img-top" src="images/3.png" alt="">
          <div class="card-body">
            <h4 class="card-title">Generate Chords/Note Sequence</h4>
            <p class="card-text">Generate chord play with note sequence and check their score</p>
          </div>
          <div class="card-footer">
            <a href="genmelodyc.jsp" class="btn btn-primary">Find Out More!</a>
          </div>
        </div>
      </div>
      
      
      
       <div class="col-lg-3 col-md-6 mb-4">
        <div class="card h-100">
          <img class="card-img-top" src="http://placehold.it/500x325" alt="">
          <div class="card-body">
            <h4 class="card-title">LilyPond and find new Sequence</h4>
            <p class="card-text">Write your own music using lilypond and find new Sequence</p>
          </div>
          <div class="card-footer">
            <a href="#" class="btn btn-primary">COMING SOON</a>
          </div>
        </div>
      </div>

      <div class="col-lg-3 col-md-6 mb-4">
        <div class="card h-100">
          <img class="card-img-top" src="http://placehold.it/500x325" alt="">
          <div class="card-body">
            <h4 class="card-title">Learn Music Sheet</h4>
            <p class="card-text">Learn Music Sheet and it's concept</p>
          </div>
          <div class="card-footer">
            <a href="#" class="btn btn-primary">COMING SOON</a>
          </div>
        </div>
      </div>
   
        </div>

		
		  <%@ include file="footer_adsense.jsp"%> 
		
        
       

      </div>
      <!-- /.row -->


	
    

    <%@ include file="addcomments.jsp"%>
    
    
    
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


  </body>

</html>