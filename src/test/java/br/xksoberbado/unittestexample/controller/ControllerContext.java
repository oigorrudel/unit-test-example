package br.xksoberbado.unittestexample.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
    "br.xksoberbado.unittestexample.controller",
    "br.xksoberbado.unittestexample.service"
})
class ControllerContext {
}
