<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<?php $this->load->view('templates/head', array('title' => 'Welcome', 'extra_css' => array('slick', 'slick-theme'))); ?>
	<style>
		.banner{
			position:absolute;
			width:100%;
			height:100vh;
			top:0;
			z-index:-100;
		}

		.banner div {
			background-size:cover;
			background-position:center;
			width:100%;
			height:100%;
		}

		.text-banner{
			background:transparent;
			padding-left:15px;
			padding-right:25%;
			padding-top:30px;
			padding-bottom:10px;
			color:white;
			font-family: "Montserrat-Light";
			letter-spacing: 2px;
			text-transform: uppercase;
			line-height: 20px;
			font-size:8px;
			color:#fffffa;
		}

		.container-footer{
			position:absolute;
			bottom:0;
		}

		@media screen and (min-width: 768px) {
			.text-banner{
				font-size:11px;
				padding-right:15px;
				width:50%;
				background-color:transparent;
				padding-left:20px;
				line-height: 25px;
			}
		}

		@media screen and (min-width: 992px) {
			.text-banner{
				width:30%;
				padding-left:60px;
			}
		}
	</style>
</head>
<body>
	<?php $this->load->view('templates/navbar', array('menuColor' => 'white')); ?>
	<div class="banner">
		<?php foreach ($photos as $photo): ?>
			<div style="background-image: url(<?= $photo?>);"></div>
		<?php endforeach ?>
	</div>
	<div class="text-banner">
		<p>We are a group of creative people who always have our treats before doing our works. Coffee and sweets are our best friend.</p>
	</div>
	<?php $this->load->view('templates/footer'); ?>
	<?php $this->load->view('templates/scripts', array('extra_js' => array('slick.min'))); ?>
	<script>
		$(document).ready(function(){
			$(".banner").slick({
				autoplay: true,
				autoplaySpeed: 2000,
				arrows: false,
				swipe:false
			});
		});
	</script>
</body>
</html>
