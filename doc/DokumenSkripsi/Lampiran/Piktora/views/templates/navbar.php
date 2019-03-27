<?php
defined('BASEPATH') OR exit('No direct script access allowed');
if (!isset($menuColor)){
	$menuColor='dark';
}
?><!--navbar-->
<table class="navigation">
	<tbody>
		<tr>
			<td class="navigation-left"><a href="/welcome"><img src="/assets/img/icon-logo-<?= $menuColor;?>.png" alt="PIKTORA"></a></td>
			<td class="navigation-right"><img id="menu" src="/assets/img/icon-menu-<?= $menuColor;?>.png" alt="Menu"></td>
		</tr>
	</tbody>
</table>
<div class="menu-fullscreen">
	<div class="menu-navigation-close">
		<a id="close-menu" href="#"><img src="/assets/img/icon-close-menu.png" alt="Close Menu"></a>
	</div>
	<div class="menu-navigation-content">
		<p>Hello, we are Piktora.</p>
		<p>Know more <a href="/about">about us</a>,</p>
		<p>explore <a href="/projects">our works</a>, and</p>
		<p>of course <a href="/contact">say hello</a> to</p>
		<p>us! We'd love to meet up</p>
		<p>over coffee (or beer)!</p>
	</div>
</div>
<!--/navbar-->
