<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<?php $this->load->view('templates/head', array('title' => 'About', 'extra_css' => array('slick', 'slick-theme'))); ?>
	<style>
		.one-page-background{
			background-color:white;
			background-size:contain;
			width:100%;
		}

		.content{
			padding-left:15px;
			padding-right:15px;
		}

		.slick-track > div{
			outline:none;
		}

		h4{
			text-transform: uppercase;
			line-height: 35px;
			font-weight: bold;
			font-family: "Montserrat-Bold";
		}

		.content-next{
			text-align:right;
		}

		.title{
			text-transform: uppercase;
			line-height: 25px;
			font-size: 11px;
			letter-spacing: 2px;
			font-weight: bold;
		}

		.description{
			font-size:10px;
			line-height: 20px;
			letter-spacing: 1px;
		}

		.content-navigation{
			letter-spacing: 3px;
			font-size:12px;
			cursor: pointer;
			margin-top: 50px;
 		}

		.content-2 h5{
			background-image: url("/assets/img/rectangle-services.png");
			height:28px;
			background-size: auto;
			background-repeat: no-repeat;
			padding-left:20px;
			padding-top:7px;
			font-size:12px;
			letter-spacing: 3px;
		}

		.content-2 .row > div{
			padding-top: 0px;
			padding-left: 0px;
			padding-right: 0px;
		}

		.content-2 .description{
			max-width: 235px;
		}

		.slick-slide img{
			display: inline;
		}

		.special-description{
			letter-spacing: 3px;
			font-weight: bold;
		}

		@media screen and (min-width: 768px) {
			h4{
				line-height: 50px;
				font-size:28px;
			}
			.one-page-background{
				background-image: url("/assets/img/about/img-background-about.jpg");
				height:100vh;
				background-size:contain;
				background-repeat:no-repeat;
				background-position:center bottom;
			}

			.content{
				padding-left:20px;
				padding-right:20px;
			}

			.content-1 div{
				max-width: 50%;
				margin-left:50%;
				text-align: right;
			}
			.content-1 div:first-child{
				margin-left:0%;	
				text-align:left;
			} 
		}

		@media screen and (min-width: 992px) {
			h4{
				line-height: 50px;
				font-size:22px;
				margin-top: 0px;
				margin-bottom: 0px;
			}

			.one-page-background{
				background-size:cover;
			}
			.content{
				padding-left:60px;
				padding-right:60px;
			}

			.content-1 div{
				width: 25%;
			}

			.content-1 div:first-child{
				float:left;
			} 

			.content-1 .content-next{
				margin-left:75%;
			}

			.content-1 .special-description{
				padding-top:18px;
				width:30%;
				margin-left:45%;
			}
		}
	</style>
</head>
<body>
	<div class="one-page-background">
		<?php $this->load->view('templates/navbar'); ?>
		<div class="content">
			<div class="content-1">
				<div>
					<p class="title">We Produce And Deliver The Highest Possible User Experience Through Innovative Design</p>
					<p class="description">PIKTORA specializes in creative and marketing material designs, often needed by companies ranging from start-up level to the prominent areas. With solid team of graphic designers, photographers, and web developers, we guarantee a creativity, originality, and identity to your brand.</p>
				</div>
				<div class="special-description">
					<h4>We Do<br>Something Fun<br>To Make<br>Something Special</h4>
				</div>
				<div class="content-navigation content-next">
					<p>WHAT WE DO <img class="icon" src="/assets/img/icon-next.png" alt="Icon-Next"></p>
				</div>
			</div>
			<div class="content-2">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-12 col-sm-6 col-md-3">
							<h5>BRANDING</h5>
							<p class="description">It all started with a discovery session where we will chat about your vision and expectation for your creative needs. Just mention and describe what you have in mind, or we can even brainstorm and gather ideas together; then we'll explore them from scratches. From there, leave the rest to us, we'll translate your creative ideas into a workable concept with a strong impression.</p>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-3">
							<h5>GRAPHIC DESIGN</h5>
							<p class="description">Let us know what your target market is. We can design every single marketing material that you need. We'll act as your artwork consultant to narrow down your needs in order to achieve your goals. We always take a closer look in every detail, to make sure that the appearance of your brand looks consistent and meaningful.</p>
						</div>
						<div class="col-xs-12 col-sm-6 col-md-3">
							<h5>WEBSITE DESIGN</h5>
							<p class="description">We believe that a good design comes with a good user experience on how people access a brand in the virtual world. Piktora specializes in developing a brand, while also collaborating with the current trend. Remember, a website is not only a tool that tells your audience about what your brand does, but also creates an impressive display in today's modern industry, as well as an affective user experience from the very beginning.</p>
						</div>	
					</div>
				</div>
				<div class="content-navigation content-prev">
					<p><img src="/assets/img/icon-previous.png" alt="Icon-Prev"> BACK TO OUR STORY</p>
				</div>
			</div>
		</div>
	</div>
	
	<?php $this->load->view('templates/scripts', array('extra_js' => array('slick.min'))); ?>
	<script>
		$(document).ready(function(){
			$(".content").slick({
				swipe : false,
				arrows: false
			});
			$(".content-next").click(function(event) {
				$(".content").slick("slickNext");
			});
			$(".content-prev").click(function(event) {
				$(".content").slick("slickPrev");
			});
		});
	</script>
</body>
</html>