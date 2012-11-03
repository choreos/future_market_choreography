source('common.R')

file <- 'cache.pdf'

plot_start()
plot_arch_portal('orch', 1, 'dotted')
plot_arch_portal('orch', 2, 'dotdash')
plot_arch_portal('orch', 3, 'dashed')
plot_arch_portal('orch', 4, 'solid')
plot_arch_portal('chor', 1, 'dotted', type='b', pch=20)
plot_end()
