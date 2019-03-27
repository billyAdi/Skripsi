<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
<head>
	<?php $this->load->view('templates/head', array('title' => 'Projects')); ?>
	<style>
		.content{
			color:black;
			margin-bottom:30px;
		}

		.content h5{
			line-height-bottom: 0px;
			padding-top:10px;
			line-height: 3px;
			letter-spacing: 2px;
			font-size:11px;
			font-weight: bold;
		}

		.content p{
			font-size:10px;
			letter-spacing: 1px; 
			margin-bottom: 10px;
		}

		.content .description{
			text-transform: uppercase;
			font-size:9px;
		}

		@media screen and (min-width: 768px) {
			.content{
				min-height:300px;
			}
		}

		@media screen and (min-width: 992px) {
			.content{
				padding-left:6.67%;
			}
		}
	</style>
</head>
<body>
	<?php $this->load->view('templates/navbar'); ?>
	<div class="container container-content">
		<div class="row">
			<?php foreach ($projects as $project): ?>
				<a href="/projects/<?= $project['id'] ?>">
					<div class="col-xs-12 col-sm-6 col-md-4 content">
						<img src="/assets/img/projects/<?= $project['id'] ?>.jpg" alt="<?= $project['title'] ?>">
						<h5><?= $project['title'] ?></h5>
						<p><?= $project['products'] ?></p>
						<p class="description">- <?= $project['categories'] ?></p>
					</div>
				</a>
			<?php endforeach; ?>
		</div>
	</div>
	<?php $this->load->view('templates/footer'); ?>
	<?php $this->load->view('templates/scripts'); ?>
</body>
</html>