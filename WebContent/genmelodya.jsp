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

    <title>Generate music sheet music score from midi file</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    

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
  "image":"http://notemusic.site/images/1.png",
  "datePublished" : "2019-06-21",
  "dateModified": "2019-06-21",
  "articleSection" : "Generate Music Sheet from MIDI file",
  "articleBody" : [ "Generate Music Sheet from MIDI file"],
  "publisher" : {
    "@type" : "Organization",
    "name" : "notemusic.site",
    "logo" :  {
      "@type": "ImageObject",
      "url": "http://notemusic.site/genmelodya.jsp"
    }
  }
}
</script>

<script>
$(document).ready(function () {

    $("#btnSubmit").click(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        // Get form
        var form = $('#fileUploadForm')[0];

		// Create an FormData object 
        var data = new FormData(form);


        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "GenerateMelody",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success : function(msg) {
				$('#output').empty();
				$('#output').append(msg);

			},
            error: function (e) {

                $("#result").text(e.responseText);
                console.log("ERROR : ", e);

            }
        });

    });

});
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
          <h1 class="mt-4">Generate Music Sheet from Midi File</h1>
          
         
          

				<form id="fileUploadForm" method="post" enctype="multipart/form-data">
					<label for="uploadMidi">Upload Your Midi File to find the Music Score</label>
					<div class="form-group">
						<input type="file" class="form-control-file" name="file" id="file"> 
						<input type="submit"class="btn btn-primary" id="btnSubmit" value="upload">
					</div>
				</form>
				
		


          
          <div id="output">






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


  </body>

</html>