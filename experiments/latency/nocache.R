source('common.R')

file <- 'nocache.pdf'
xmax <- 225
ymax <- 20
x_axis <- seq(25,xmax,25)

plot_start()
plot_arch_portal('orch', 1, 'dotted')
plot_arch_portal('orch', 2, 'dotdash')
plot_arch_portal('orch', 3, 'dashed')
plot_arch_portal('orch', 4, 'solid')
plot_arch_portal('chor', 1, 'dotted', type='b', pch=20)
plot_arch_portal('chor', 2, 'dotdash', type='b', pch=20)
plot_arch_portal('chor', 3, 'dashed', type='b', pch=20)
plot_arch_portal('chor', 4, 'solid', type='b', pch=20)
plot_end()
