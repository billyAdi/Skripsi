<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<?php $this->load->view('templates/head', array('title' => 'Project Details')); ?>
	<style>
		.content-text{
			background-color:#a59c93;
			color:#4f4745;
			padding-top: 15px;
			padding-left: 15px;
			padding-bottom: 40px;
		}

		.content-text a{
			color:#4f4745;
			text-decoration: underline;
			font-weight: bold;
		}

		.container-content-text{
			padding-left: 0px;
		}

		.container-content-img img{
			margin-bottom: 20px;
			max-width: 80%;
		}

		.back-to-previous{
			margin-top:20px;
		}

		.back-to-previous a{
			color:#4f4745;
			font-weight: bold;
			text-transform: uppercase;
			letter-spacing: 3px;
			font-size:10px;
		}

		.content-text p{
			font-size: 10px;
			letter-spacing: 1px;
		}

		h5{
			letter-spacing: 2px;
			font-size: 11px;
		}

		.content-text > div{
			margin-bottom: 30px;
		}

		@media screen and (min-width: 768px) {
			.content-text{
				padding-top: 30px;
				padding-left: 20px;
				padding-bottom: 250px;
			}

			.container-content-img img{
				max-width: 100%;
				padding-right:10px;
			}

			.back-to-previous{
				padding-left:50px;
			}

			.container-content-img{
				overflow-y: auto;
			}

			.container-content-text{
				overflow-y: auto;
			}
		}

		@media screen and (min-width: 992px) {
			.container-content-text{
				padding-right:80px;
			}

			.content-text{
				padding-left: 60px;
				padding-right:60px;
				padding-bottom: 100px;
			}

			.container-content-img img{
				padding-right:50px;
			}

		}
	</style>
</head>
<body>
	<?php $this->load->view('templates/navbar'); ?>
	<div class="container-fluid container-all">
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-sm-push-6 col-md-7 col-md-push-5 container-content-img">
				<?php foreach($photos as $photo): ?>
					<img src="<?= $photo?>" alt="<?= $title?>">
				<?php endforeach; ?>
			</div>	
			<div class="col-xs-12 col-sm-6 col-sm-pull-6 col-md-5 col-md-pull-7 container-content-text">
				<div class="content-text">
					<div>
						<h5><?= $title ?></h5>
						<p><?= $products ?></p>
						<p>- <?= $categories ?></p>
					</div>
					<div>
						<h5>About Client</h5>
						<p><?= $aboutClient ?></p>
					</div>
					<div>
						<h5>Our Concept</h5>
						<p><?= $ourConcept ?></p>
					</div>
					<?= $additionalInfo ?>
				</div>
				<div class="back-to-previous">
					<p><a href="/projects"><img src="/assets/img/icon-previous.png" alt=""> See All Projects</a></p>
				</div>
			</div>
		</div>	
	</div>
</div>
<?php $this->load->view('templates/scripts'); ?>
<script>
	$(document).ready(function() {
		function setContentHeight(){
			var height = $(window).height()-$(".navigation").height()-50;
			$('.container-content-img').css( "height", (height + 'px'));
			$('.container-content-text').css( "height", (height + 'px'));
		}
		if($( window ).width()>=768){
			setContentHeight();	
		}
		$( window ).resize(function() {
			if($( window ).width()>=768){
				setContentHeight();	
			}
		});	
	});
</script>
</body>
</html>