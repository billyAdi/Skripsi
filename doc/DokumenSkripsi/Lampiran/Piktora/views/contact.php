<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<?php $this->load->view('templates/head', array('title' => 'Contact', 'extra_css'=> array('font-awesome.min'))); ?>
	<style>
		body{
			background-color:white;
		}

		.form-control{
			color:#474342;
			background-color:#eeeae9;
			border:0px;
		}

		.captcha-container > div {
			padding-left:0px;
			padding-right:0px;
		}

		.captcha-container div .form-control{
			height:50px;
			text-align:center;
		}

		.captcha-container div:first-child .form-control{
			background-color:white;
			border:1px solid;
		}

		.send-button{
			background:none;
			border:none;
			box-shadow: none;
			padding-left:0px;
			text-transform: uppercase;
			font-size:11px;
			letter-spacing: 3px;
		}

		.send-button:hover{
			background:none;
		}

		.captcha-container div:last-child{
			margin-top:20px;
		}

		.container-title{
			padding-right:20%;
		}

		.title{
			margin-top:0px;
			letter-spacing: 2px;
			font-family: "Montserrat-Bold";
		}

		.description{
			font-size:11px;
			text-transform: uppercase;
			letter-spacing: 2px;
			line-height: 25px;
		}

		#captcha{
			width:100%;
			height:50px;
		}
		
		@media screen and (min-width: 768px) {
			.container-contact{
				padding-left:20px;
				padding-right:20px;
			}

			.container-footer{
				position:absolute;
				bottom:0;
			}

			div .captcha-code{
				padding-right: 10px;
			}

			div .captcha-input{
				padding-left: 10px;
			}
		}

		@media screen and (min-width: 992px) {
			.container-contact{
				padding-left:60px;
				padding-right:60px;
			}

			.container-title{
				padding-right:0px;
			}

			.captcha-container div:last-child{
				margin-top:0px;
			}
		}

	</style>
</head>
<body>
	<?php $this->load->view('templates/navbar'); ?>
	<?php $this->load->view('templates/flashmessage'); ?>
	<div class="container-fluid container-contact">
		<div class="row">
			<div class="col-xs-12 col-sm-6 col-md-3 container-title">
				<h2 class="title">SAY HELLO ,</h2>
				<p class="description">Don’t hesitate to know us more.</p>
			</div>
			<div class="col-xs-12 col-sm-6 col-md-6">
				<?= form_open('/contact'); ?>
					<div class="form-group">
						<input type="text" name="name" class="form-control" placeholder="Your Name" required="required">
					</div>
					<div class="form-group">
						<input type="email" name="email" class="form-control" placeholder="Email Address" required="required">
					</div>
					<div class="form-group">
						<input type="text" name="subject" class="form-control" placeholder="Subject:" required="required">
					</div>
					<div class="form-group">
						<textarea class="form-control" name="message" rows="7" placeholder="What do you want to say?" required="required"></textarea>
					</div>
					<div class="form-group captcha-container">
						<div class="col-xs-6 col-md-3 captcha-code">
							<?= $captcha; ?>
						</div>
						<div class="col-xs-6 col-md-3 captcha-input">
							<input type="text" name="captcha" class="form-control" placeholder="Enter the code" required="required">
						</div>
						<div class="col-xs-6 col-md-3 col-md-offset-3">
							<button type="submit" class="btn btn-default send-button">Send Now <img src="/assets/img/icon-next.png" alt="Next Arrow"></button>
						</div>
					</div>
				<?= form_close(); ?>
			</div>
		</div>	
	</div>
</div>
<?php $this->load->view('templates/footer'); ?>
<?php $this->load->view('templates/scripts'); ?>
</body>
</html>
